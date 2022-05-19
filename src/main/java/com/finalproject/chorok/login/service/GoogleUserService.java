package com.finalproject.chorok.login.service;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.finalproject.chorok.login.dto.GoogleUserInfoDto;
import com.finalproject.chorok.login.dto.GoogleUserResponseDto;
import com.finalproject.chorok.login.model.Labeling;
import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.login.repository.LabelingRepository;
import com.finalproject.chorok.login.repository.UserRepository;
import com.finalproject.chorok.security.GoogleOAuthRequest;
import com.finalproject.chorok.security.GoogleOAuthResponse;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.security.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import software.amazon.ion.Decimal;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoogleUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final LabelingRepository labelingRepository;

    @Autowired
    public GoogleUserService(UserRepository userRepository, PasswordEncoder passwordEncoder, LabelingRepository labelingRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.labelingRepository = labelingRepository;
    }

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;


    public GoogleUserResponseDto googleLogin(String accessToken) throws JsonProcessingException {
        //HTTP Request를 위한 RestTemplate
        RestTemplate restTemplate = new RestTemplate();

//        // 1. "인가 코드"로 "액세스 토큰" 요청
//        String accessToken = getAccessToken(restTemplate, code);
        // 2. "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        GoogleUserInfoDto snsUserInfoDto = getGoogleUserInfo(restTemplate, accessToken);
        // 3. "구글 사용자 정보"로 필요시 회원가입  및 이미 같은 이메일이 있으면 기존회원으로 로그인
        User googleUser = registerGoogleIfNeeded(snsUserInfoDto);

        // 4. 강제 로그인 처리
        final String AUTH_HEADER = "Authorization";
        final String TOKEN_TYPE = "BEARER";

        String jwt_token = forceLogin(googleUser); // 로그인처리 후 토큰 받아오기
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_HEADER, TOKEN_TYPE + " " + jwt_token);
        GoogleUserResponseDto googleUserResponseDto = GoogleUserResponseDto.builder()
                .token(TOKEN_TYPE + " " + jwt_token)
                .username(googleUser.getUsername())
                .nickname(googleUser.getNickname())
                .build();
        System.out.println("Google user's token : " + TOKEN_TYPE + " " + jwt_token);
        System.out.println("LOGIN SUCCESS!");
        return googleUserResponseDto;
    }


    private String getAccessToken(RestTemplate restTemplate, String code) throws JsonProcessingException {

        //Google OAuth Access Token 요청을 위한 파라미터 세팅
        GoogleOAuthRequest googleOAuthRequestParam = GoogleOAuthRequest
                .builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(code)
//                .redirectUri("https://memegle.xyz/redirect/google")
//                .redirectUri("http://localhost:3000/redirect/google")
                .redirectUri("http://localhost:8080/auth/google/callback")
                .grantType("authorization_code")
                .accessType("offline")
                .scope("openid https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email").build();


        //JSON 파싱을 위한 기본값 세팅
        //요청시 파라미터는 스네이크 케이스로 세팅되므로 Object mapper에 미리 설정해준다.
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //AccessToken 발급 요청
        ResponseEntity<String> resultEntity = restTemplate.postForEntity("https://oauth2.googleapis.com/token", googleOAuthRequestParam, String.class);
        System.out.println(resultEntity+"resultEntity");
        //Token Request
        GoogleOAuthResponse result = mapper.readValue(resultEntity.getBody(), new TypeReference<GoogleOAuthResponse>() {
        });
        String accessToken = result.getAccess_token();
        System.out.println(accessToken);
        return accessToken;
    }


    private GoogleUserInfoDto getGoogleUserInfo(RestTemplate restTemplate, String accessToken) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String requestUrl = UriComponentsBuilder.fromHttpUrl("https://www.googleapis.com/oauth2/v3/userinfo")
                .queryParam("access_token", accessToken).encode().toUriString();

        String resultJson = restTemplate.getForObject(requestUrl, String.class);

        Map<String,String> userInfo = mapper.readValue(resultJson, new TypeReference<Map<String, String>>(){});
        String googleId = userInfo.get("sub");
        Decimal googleIdContainer = Decimal.valueOf(googleId);

        return GoogleUserInfoDto.builder()
                .googleId(googleIdContainer)
                .email(userInfo.get("email"))
                .nickname(userInfo.get("name"))
                .profileImage(userInfo.get("picture"))
                .build();
    }

    private User registerGoogleIfNeeded(GoogleUserInfoDto googleUserInfoDto) {

        // DB 에 중복된 google Id 가 있는지 확인
        Decimal googleUserId = googleUserInfoDto.getGoogleId();
        User googleUser = userRepository.findByGoogleId(googleUserId)
                .orElse(null);
        String nickname = googleUserInfoDto.getNickname();

        if (googleUser == null) {
            // 회원가입
            // username: google ID(email)
            //카카오 사용자 이메일과 동일한 이메일을 가진 회원이 있는지 확인

            String googleEmail = googleUserInfoDto.getEmail();
            User sameEmailUser = userRepository.findByUsername(googleEmail).orElse(null);
            if (sameEmailUser != null) {
                googleUser = sameEmailUser;
                // 기존 회원정보에 카카오 Id 추가
                googleUser.setGoogleId(googleUserId);
            } else {
                // 신규 회원가입
                // 닉네임 중복검사
                User sameNicknameUser = userRepository.findByNickname(nickname).orElse(null);
                if(sameNicknameUser != null){
                    nickname = UUID.randomUUID().toString().substring(3,10);
                }
                // password: random UUID
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);
                // email: kakao email
                String email = googleUserInfoDto.getEmail();
                String profileImage = googleUserInfoDto.getProfileImage();

                googleUser = new User(email, encodedPassword, nickname, null, googleUserId, profileImage);
                Labeling defaultLabeling = new Labeling(googleUser);
                labelingRepository.save(defaultLabeling);
            }
            userRepository.save(googleUser); }
        return googleUser; }


    private String forceLogin(User googleUser) {
        UserDetailsImpl userDetails = new UserDetailsImpl(googleUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("강제로그인 초입부분");
//        UserDetailsImpl userDetails = UserDetailsImpl.builder()
//                .username(googleUser.getUsername())
//                .password(googleUser.getPassword())
//                .build();
        System.out.println(userDetails.getUsername()+"유저디테일즈 출력여부");


        return JwtTokenUtils.generateJwtToken(userDetails);
    }
}