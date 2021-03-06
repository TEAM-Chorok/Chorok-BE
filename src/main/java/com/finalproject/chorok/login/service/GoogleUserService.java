package com.finalproject.chorok.login.service;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.finalproject.chorok.common.utils.RedisUtil;
import com.finalproject.chorok.login.dto.GoogleUserInfoDto;
import com.finalproject.chorok.login.dto.GoogleUserResponseDto;
import com.finalproject.chorok.login.dto.UserResponseDto;
import com.finalproject.chorok.login.model.Labeling;
import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.login.repository.LabelingRepository;
import com.finalproject.chorok.login.repository.UserRepository;
import com.finalproject.chorok.post.utils.CommUtils;
import com.finalproject.chorok.security.GoogleOAuthRequest;
import com.finalproject.chorok.security.GoogleOAuthResponse;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.security.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoogleUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final LabelingRepository labelingRepository;
    private final RedisUtil redisUtil;
    private final CommUtils commUtils;

    @Autowired
    public GoogleUserService(UserRepository userRepository, PasswordEncoder passwordEncoder, LabelingRepository labelingRepository, RedisUtil redisUtil, CommUtils commUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.labelingRepository = labelingRepository;
        this.redisUtil = redisUtil;
        this.commUtils = commUtils;
    }

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;


    public GoogleUserResponseDto googleLogin(String accessToken) throws JsonProcessingException {
        //HTTP Request??? ?????? RestTemplate
        RestTemplate restTemplate = new RestTemplate();

//        // 1. "?????? ??????"??? "????????? ??????" ??????
//        String accessToken = getAccessToken(restTemplate, code);
        // 2. "????????? ??????"?????? "????????? ????????? ??????" ????????????
        GoogleUserInfoDto snsUserInfoDto = getGoogleUserInfo(restTemplate, accessToken);
        // 3. "?????? ????????? ??????"??? ????????? ????????????  ??? ?????? ?????? ???????????? ????????? ?????????????????? ?????????
        User googleUser = registerGoogleIfNeeded(snsUserInfoDto);

        // 4. ?????? ????????? ??????
        final String AUTH_HEADER = "Authorization";
        final String TOKEN_TYPE = "BEARER";

        String jwt_token = forceLogin(googleUser); // ??????????????? ??? ?????? ????????????
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_HEADER, TOKEN_TYPE + " " + jwt_token);
        GoogleUserResponseDto googleUserResponseDto = GoogleUserResponseDto.builder()
                .token(TOKEN_TYPE + " " + jwt_token)
                .username(googleUser.getUsername())
                .nickname(googleUser.getNickname())
                .build();
        return googleUserResponseDto;
    }


    private String getAccessToken(RestTemplate restTemplate, String code) throws JsonProcessingException {

        //Google OAuth Access Token ????????? ?????? ???????????? ??????
        GoogleOAuthRequest googleOAuthRequestParam = GoogleOAuthRequest
                .builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(code)
                .redirectUri("https://chorok.kr/auth/google/callback")
                .grantType("authorization_code")
                .accessType("offline")
                .scope("openid https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email").build();


        //JSON ????????? ?????? ????????? ??????
        //????????? ??????????????? ???????????? ???????????? ??????????????? Object mapper??? ?????? ???????????????.
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //AccessToken ?????? ??????
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
        redisUtil.set(googleId, accessToken, 60);

        return GoogleUserInfoDto.builder()
                .googleId(googleId)
                .email(userInfo.get("email"))
                .nickname(userInfo.get("name"))
                .profileImage(userInfo.get("picture"))
                .build();
    }

    private User registerGoogleIfNeeded(GoogleUserInfoDto googleUserInfoDto) {

        // DB ??? ????????? google Id ??? ????????? ??????
        String googleUserId = googleUserInfoDto.getGoogleId();
        User googleUser;
        googleUser = userRepository.findByGoogleId(googleUserId)
                .orElse(null);
        String nickname = googleUserInfoDto.getNickname();

        if (googleUser == null) {
            // ????????????
            // username: google ID(email)
            //????????? ????????? ???????????? ????????? ???????????? ?????? ????????? ????????? ??????

            String googleEmail = googleUserInfoDto.getEmail();
            User sameEmailUser = userRepository.findByUsername(googleEmail).orElse(null);
            if (sameEmailUser != null) {
                googleUser = sameEmailUser;
                // ?????? ??????????????? ?????? Id ??????
                googleUser.setGoogleId(googleUserId);
            } else {
                // ?????? ????????????
                // ????????? ????????????
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


        public HashMap<String, String> googleRevokeAccess(String accessToken, String googleId)
    {
        try{
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost("https://accounts.google.com/o/oauth2/revoke?token="+accessToken);
            org.apache.http.HttpResponse response = client.execute(post);
            redisUtil.delete(googleId);
        }
        catch(IOException e)
        {
        }
    return commUtils.responseHashMap(HttpStatus.OK);
    }

    private String forceLogin(User googleUser) {
        UserDetailsImpl userDetails = new UserDetailsImpl(googleUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return JwtTokenUtils.generateJwtToken(userDetails);
    }
}