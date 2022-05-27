package com.finalproject.chorok.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.finalproject.chorok.common.Image.S3Uploader;
import com.finalproject.chorok.common.utils.RedisUtil;
import com.finalproject.chorok.common.utils.StatusMessage;
import com.finalproject.chorok.login.dto.*;
import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.login.repository.UserRepository;
import com.finalproject.chorok.login.service.GoogleUserService;
import com.finalproject.chorok.login.service.KakaoUserService;
import com.finalproject.chorok.login.service.UserService;
import com.finalproject.chorok.plant.service.PlantFilterService;
import com.finalproject.chorok.login.dto.LabelingResponseDto;
import com.finalproject.chorok.post.utils.CommUtils;
import com.finalproject.chorok.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.ion.Decimal;

import javax.activity.InvalidActivityException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@RestController
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;
    private final GoogleUserService googleUserService;
    private final S3Uploader s3Uploader;
    private final PlantFilterService plantFilterService;
    private final UserRepository userRepository;
    private final CommUtils commUtils;
    private final RedisUtil redisUtil;


    @Autowired
    public UserController(UserService userService, KakaoUserService kakaoUserService, GoogleUserService googleUserService, S3Uploader s3Uploader, PlantFilterService plantFilterService, UserRepository userRepository, CommUtils commUtils, RedisUtil redisutil) {
        this.userService = userService;
        this.kakaoUserService  = kakaoUserService;
        this.googleUserService = googleUserService;
        this.s3Uploader = s3Uploader;
        this.plantFilterService = plantFilterService;
        this.userRepository = userRepository;
        this.commUtils = commUtils;
        this.redisUtil = redisutil;
    }

    //아이디 중복 체크
    @PostMapping("/auth/emailCheck")
    private ResponseEntity<HashMap<String, String>> usernameDupliChk(@RequestBody DuplicateChkDto duplicateChkDto) {
        String msg = userService.usernameDuplichk(duplicateChkDto);
        if (msg.equals("사용가능한 이메일 입니다.")) {
            return ResponseEntity.status(HttpStatus.OK).body(commUtils.responseHashMap(HttpStatus.OK));

        } else {
            return ResponseEntity.status(HttpStatus.OK).body(commUtils.responseHashMap(HttpStatus.BAD_REQUEST));
        }
    }

    //닉네임 중복 체크
    @PostMapping("/auth/nicknameCheck")
    private ResponseEntity<HashMap<String, String>> nicknameDupliChk(@RequestBody DuplicateChkDto duplicateChkDto){
        String msg = userService.nicknameDuplichk(duplicateChkDto);
        if(msg.equals("사용가능한 닉네임 입니다.")){
            return ResponseEntity.status(HttpStatus.OK).body(commUtils.responseHashMap(HttpStatus.OK));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(commUtils.responseHashMap(HttpStatus.BAD_REQUEST));
        }
    }

//     회원 가입 요청 처리
    @PostMapping("/auth/signUp")
    public ResponseEntity<HashMap<String, String>> registerUser(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "nickname") String nickname,
            @RequestParam(value = "profileImgUrl", required = false) MultipartFile multipartFile
    ) throws IOException {

        String profileImgUrl = null;

        if(multipartFile!=null){
            profileImgUrl = s3Uploader.upload(multipartFile, "static");
        }
        SignupRequestDto signupRequestDto = new SignupRequestDto(username, password, nickname, profileImgUrl);
        return ResponseEntity.status(HttpStatus.OK).body(userService.registerUser(signupRequestDto));

    }


    //카카오 로그인
    @GetMapping("/auth/kakao/callback")
    public ResponseEntity<KakaoUserResponseDto> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(kakaoUserService.kakaoLogin(code));
    }

    //구글 로그인
    @GetMapping("/auth/google/callback")
    public ResponseEntity<GoogleUserResponseDto> googleLogin(@RequestParam String code) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(googleUserService.googleLogin(code));
    }


    //임시 비밀번호 보내기
    @PostMapping("/auth/send-temp-password")
    public ResponseEntity<CMResponseDto> sendTempPassword(@RequestBody @Valid EmailRequestDto emailRequestDto) throws InvalidActivityException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.sendTempPassword(emailRequestDto));
    }

    //로그인 확인

    @GetMapping("/user/isLogIn")
    private ResponseEntity<IsLoginDto> isloginChk(@AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.isloginChk(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(userService.isloginChk(userDetails));
    }

    //이메일인증 버튼 클릭시 토큰확인
    @GetMapping("/auth/check-email-token")
    public ResponseEntity<UserResponseDto> checkEmailToken(String token, String email, HttpServletResponse response) throws InvalidActivityException {
        UserResponseDto userResponseDto = userService.checkEmailToken(token, email);
//        response.setHeader("Authorization", userResponseDto.getToken());
//        response.sendRedirect("https://chorok.kr/home");
//        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
        try {
            response.setHeader("Authorization", userResponseDto.getToken());
            response.sendRedirect("https://chorok.kr/home");
            return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
        } catch (IOException e) {
            throw new InvalidActivityException("유효하지 않은 주소입니다.");
        }

    }

    // 식물 추천 테스트
    @PutMapping("/user/labeling")
    public ResponseEntity<LabelingResponseDto> labelingTest(@RequestBody LabelingDto labelingDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.updateLabeling(labelingDto, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(userService.getLabelingPlant(labelingDto));
    }

    // 메인페이지 식물 추천 테스트 조회
    @GetMapping("/user/labeling/results")
    public ResponseEntity<List<LabelingResponseDto>> getLabelingResults(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getLabelingResults(userDetails));
    }

    // 모든 로그인 로그아웃
    @GetMapping("/user/allLogOut")
    public ResponseEntity<HashMap<String, String>> allLogOut(@AuthenticationPrincipal UserDetailsImpl userDetails) throws JsonProcessingException {
        User user = userDetails.getUser();
        if(user.getKakaoId()!=null){
            return ResponseEntity.status(HttpStatus.OK).body(kakaoUserService.kakaoLogout(user.getKakaoId()));

        } else if(user.getGoogleId()!=null){
            String googleId = user.getGoogleId();
            String accessToken = (String)redisUtil.get(googleId);
            return ResponseEntity.status(HttpStatus.OK).body(googleUserService.googleRevokeAccess(accessToken, googleId));
        }
        return ResponseEntity.status(HttpStatus.OK).body(commUtils.responseHashMap(HttpStatus.OK));
    }
    //CI/CD 응답
    @GetMapping("/auth") public String checkHealth() {
        return "푸시 했을때만!~";
    }
}

