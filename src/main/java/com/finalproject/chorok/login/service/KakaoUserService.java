package com.finalproject.chorok.login.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.chorok.login.dto.KakaoUserInfoDto;
import com.finalproject.chorok.login.dto.KakaoUserResponseDto;
import com.finalproject.chorok.login.model.Labeling;
import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.login.repository.LabelingRepository;
import com.finalproject.chorok.login.repository.UserRepository;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.security.jwt.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class KakaoUserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final LabelingRepository labelingRepository;

    @Autowired
    public KakaoUserService(UserRepository userRepository, PasswordEncoder passwordEncoder, LabelingRepository labelingRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.labelingRepository = labelingRepository;
    }

    public KakaoUserResponseDto kakaoLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);
        System.out.println("1.\"인가 코드\"로 \"액세스 토큰\" 요청");
        // 2. 토큰으로 카카오 API 호출
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
        System.out.println("2. 토큰으로 카카오 API 호출");
        // 3. 필요시에 회원가입
//        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);
        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);
        System.out.println("3. 필요시에 회원가입");
        // 4. 강제 로그인 처리
        System.out.println("4. 강제 로그인 처리");
        final String AUTH_HEADER = "Authorization";
        final String TOKEN_TYPE = "BEARER";

        String jwt_token = forceLogin(kakaoUser); // 로그인처리 후 토큰 받아오기
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_HEADER, TOKEN_TYPE + " " + jwt_token);
        KakaoUserResponseDto kakaoUserResponseDto = KakaoUserResponseDto.builder()
                .token(TOKEN_TYPE + " " + jwt_token)
                .userId(kakaoUser.getUserId())
                .nickname(kakaoUser.getNickname())
                .email(kakaoUser.getUsername())
                .build();
        System.out.println("kakao user's token : " + TOKEN_TYPE + " " + jwt_token);
        System.out.println("LOGIN SUCCESS!");
        System.out.println(kakaoUserResponseDto.getUserId());
        return kakaoUserResponseDto;

    }

    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "5c75f19b556cbab66949ba9276da5237");
        body.add("redirect_uri", "http://localhost:3000/auth/kakao/callback");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        System.out.println();
        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        System.out.println(accessToken);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        System.out.println("jsonNode");
        System.out.println(jsonNode);

        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = null;
        String profileImage = null;
        if(jsonNode.get("kakao_account").get("email")==null){
        } else {
            email = jsonNode.get("kakao_account").get("email").asText();
        }
        System.out.println("email 받아와짐");

        if(jsonNode.get("properties").get("profile_image")==null){
        } else {
            profileImage = jsonNode.get("properties").get("profile_image").asText();
        }
        System.out.println(profileImage);

        System.out.println("카카오 사용자 정보: " + id + ", " + nickname + ", " + email+ ", " + profileImage);
        return new KakaoUserInfoDto(id, nickname, email, profileImage);
    }

    private User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) { // DB Kakao Id
        Long kakaoId = kakaoUserInfo.getId();
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);
        String nickname = kakaoUserInfo.getNickname();

        if (kakaoUser == null) {
            //카카오 사용자 이메일과 동일한 이메일을 가진 회원이 있는지 확인
            String kakaoEmail = kakaoUserInfo.getEmail();
            String profileImage = kakaoUserInfo.getProfileImage();
            User sameEmailUser = userRepository.findByUsername(kakaoEmail).orElse(null);
            if (sameEmailUser != null) {
                kakaoUser = sameEmailUser;
                // 기존 회원정보에 카카오 Id 추가
                kakaoUser.setKakaoId(kakaoId);
            } else {
                // 신규 회원가입
                // 닉네임 중복검사
                User sameNicknameUser = userRepository.findByNickname(nickname).orElse(null);
                if(sameNicknameUser != null){
                    nickname = UUID.randomUUID().toString().substring(5,12);
                }
                // password: random UUID
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);
                // email: kakao email
                String email = kakaoUserInfo.getEmail();
                kakaoUser = new User(email, encodedPassword, nickname, kakaoId, profileImage);
                Labeling defaultLabeling = new Labeling(kakaoUser);
                labelingRepository.save(defaultLabeling);
            }
            userRepository.save(kakaoUser);
        }

        return kakaoUser; }


    private String forceLogin(User kakaoUser) {
        UserDetailsImpl userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return JwtTokenUtils.generateJwtToken(userDetails);
    }
}