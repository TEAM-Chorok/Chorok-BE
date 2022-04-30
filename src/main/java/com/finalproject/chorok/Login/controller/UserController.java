package com.finalproject.chorok.Login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.finalproject.chorok.Login.dto.*;
import com.finalproject.chorok.Login.service.GoogleUserService;
import com.finalproject.chorok.Login.service.KakaoUserService;
import com.finalproject.chorok.Login.service.UserService;
import com.finalproject.chorok.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.activity.InvalidActivityException;
import javax.validation.Valid;


@RestController
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;
    private final GoogleUserService googleUserService;

    @Autowired
    public UserController(UserService userService, KakaoUserService kakaoUserService, GoogleUserService googleUserService) {
        this.userService = userService;
        this.kakaoUserService  = kakaoUserService;
        this.googleUserService = googleUserService;
    }

    // 회원 가입 요청 처리
    @PostMapping("auth/signUp")
    public void registerUser(@RequestBody SignupRequestDto requestDto) {
        userService.registerUser(requestDto);
    }

    //카카오 로그인
    @GetMapping("/auth/kakao/callback")
    public void kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        System.out.println("제일 시작점");
        kakaoUserService.kakaoLogin(code);

    }

    //구글 로그인
    @GetMapping("/auth/google/callback")
    public void googleLogin(@RequestParam String code) throws JsonProcessingException {

        System.out.println("구글로그인 시작");
        ResponseDto.<GoogleUserResponseDto>builder()
                .status(HttpStatus.OK.toString())
                .message("구글 소셜 로그인 요청")
                .data(googleUserService.googleLogin(code))
                .build();
    }


    //임시 비밀번호 보내기
    @PostMapping("/auth/send-temp-password")
    public ResponseEntity<CMResponseDto> sendTempPassword(@RequestBody @Valid EmailRequestDto emailRequestDto) throws InvalidActivityException {
        return userService.sendTempPassword(emailRequestDto);
    }

    //로그인 확인

    @GetMapping("/user/isLogIn")
    private ResponseEntity<IsLoginDto> isloginChk(@AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println("시작");
        System.out.println(userDetails);
        userService.isloginChk(userDetails);

        return new ResponseEntity<>(userService.isloginChk(userDetails),HttpStatus.OK);

    }

//
//    @GetMapping("/auth/check-email-token")
//    public void checkEmailToken(String token, String email, HttpServletResponse response) {
//        userService.checkEmailToken(token, email);
//        try {
//            response.sendRedirect("https://localhost:8080/signup/complete");
//
//        } catch (IOException e) {
//            throw new InvalidException("유효하지 않은 주소입니다.");
//        }
//
//    }

}