package com.finalproject.chorok.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.finalproject.chorok.common.Image.S3Uploader;
import com.finalproject.chorok.common.utils.StatusMessage;
import com.finalproject.chorok.login.dto.*;
import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.login.service.GoogleUserService;
import com.finalproject.chorok.login.service.KakaoUserService;
import com.finalproject.chorok.login.service.UserService;
import com.finalproject.chorok.plant.service.PlantFilterService;
import com.finalproject.chorok.login.dto.LabelingResponseDto;
import com.finalproject.chorok.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activity.InvalidActivityException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;
    private final GoogleUserService googleUserService;
    private final S3Uploader s3Uploader;
    private final PlantFilterService plantFilterService;

    @Autowired
    public UserController(UserService userService, KakaoUserService kakaoUserService, GoogleUserService googleUserService, S3Uploader s3Uploader, PlantFilterService plantFilterService) {
        this.userService = userService;
        this.kakaoUserService  = kakaoUserService;
        this.googleUserService = googleUserService;
        this.s3Uploader = s3Uploader;
        this.plantFilterService = plantFilterService;
    }

    //아이디 중복 체크
    @PostMapping("/auth/usernameCheck")
    private ResponseEntity<StatusMessage> usernameDupliChk(@RequestBody DuplicateChkDto duplicateChkDto) {
        StatusMessage statusMessage = new StatusMessage();
        String msg = userService.usernameDuplichk(duplicateChkDto);
        if (msg.equals("사용가능한 이메일 입니다.")) {
            statusMessage.setStatusCode(StatusMessage.StatusEnum.OK);
            statusMessage.setMessage(msg);
            return new ResponseEntity<>(statusMessage, HttpStatus.OK);
        } else {
            statusMessage.setStatusCode(StatusMessage.StatusEnum.BAD_REQUEST);
            statusMessage.setMessage(msg);
            return new ResponseEntity<>(statusMessage, HttpStatus.BAD_REQUEST);
        }
    }

    //닉네임 중복 체크
    @PostMapping("/auth/nicknameCheck")
    private ResponseEntity<StatusMessage> nicknameDupliChk(@RequestBody DuplicateChkDto duplicateChkDto){
        StatusMessage statusMessage = new StatusMessage();
        String msg = userService.nicknameDuplichk(duplicateChkDto);
        if(msg.equals("사용가능한 닉네임 입니다.")){
            statusMessage.setStatusCode(StatusMessage.StatusEnum.OK);
            statusMessage.setMessage(msg);
            return new ResponseEntity<>(statusMessage,HttpStatus.OK);
        }else{
            statusMessage.setStatusCode(StatusMessage.StatusEnum.BAD_REQUEST);
            statusMessage.setMessage(msg);
            return new ResponseEntity<>(statusMessage, HttpStatus.BAD_REQUEST);
        }
    }

    // 회원 가입 요청 처리
    @PostMapping("auth/signUp")
    public String registerUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("nickname") String nickname,
            @RequestParam(value = "profileImgUrl", required = false) MultipartFile multipartFile
            ) throws IOException {
        System.out.println("실행");
        String profileImgUrl = null;

        System.out.println(multipartFile);
        if(!multipartFile.isEmpty()){
        profileImgUrl = s3Uploader.upload(multipartFile, "static");}
        SignupRequestDto signupRequestDto = new SignupRequestDto(username, password, nickname, profileImgUrl);
        return userService.registerUser(signupRequestDto);
    }

    //카카오 로그인
    @GetMapping("/auth/kakao/callback")
    public KakaoUserResponseDto kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        System.out.println("제일 시작점");
        return kakaoUserService.kakaoLogin(code);

    }

    //구글 로그인
    @GetMapping("/auth/google/callback")
    public GoogleUserResponseDto googleLogin(@RequestParam String code) throws JsonProcessingException {
        System.out.println("구글로그인 시작");
        return googleUserService.googleLogin(code);
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


    @GetMapping("/auth/check-email-token")
    public void checkEmailToken(String token, String email, HttpServletResponse response) throws InvalidActivityException {
        System.out.println("이메일 토큰 인증과정 시작");
        userService.checkEmailToken(token, email);
        try {
            response.sendRedirect("http://localhost:8080/auth/logIn");
            System.out.println("redirect 시키기");
        } catch (IOException e) {
            throw new InvalidActivityException("유효하지 않은 주소입니다.");
        }

    }
    //임시 비밀번호 보내기
    @PostMapping("/user/labeling/test")
    public void labelingTest(@AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.labelingTest(userDetails);
    }

    // 식물 추천 테스트
    @PutMapping("/user/labeling")
    public LabelingResponseDto labelingTest(@RequestBody LabelingDto labelingDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.updateLabeling(labelingDto, userDetails);
        return userService.getLabelingPlant(labelingDto);

    }

    // 메인페이지 식물 추천 테스트 조회
    @GetMapping("/user/labeling/results")
    public List<LabelingResponseDto> getLabelingResults(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("컨트롤러 들어오나");
        return userService.getLabelingResults(userDetails);
    }

    // 모든 로그인 로그아웃
    @GetMapping("/auth/AllLogOut/")
    public String allLogOut(@AuthenticationPrincipal UserDetailsImpl userDetails) throws JsonProcessingException {
        System.out.println("올아웃 컨트롤러 들어오나");
        User user = userDetails.getUser();
        if(user.getKakaoId()!=null){
            System.out.println("카카오아이디 이프문 통과하나");
            return kakaoUserService.kakaoLogout(user.getKakaoId());
        }
        else {}
        return "일반 로그아웃";
    }
}