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

    //????????? ?????? ??????
    @PostMapping("/auth/emailCheck")
    private ResponseEntity<HashMap<String, String>> usernameDupliChk(@RequestBody DuplicateChkDto duplicateChkDto) {
        String msg = userService.usernameDuplichk(duplicateChkDto);
        if (msg.equals("??????????????? ????????? ?????????.")) {
            return ResponseEntity.status(HttpStatus.OK).body(commUtils.responseHashMap(HttpStatus.OK));

        } else {
            return ResponseEntity.status(HttpStatus.OK).body(commUtils.responseHashMap(HttpStatus.BAD_REQUEST));
        }
    }

    //????????? ?????? ??????
    @PostMapping("/auth/nicknameCheck")
    private ResponseEntity<HashMap<String, String>> nicknameDupliChk(@RequestBody DuplicateChkDto duplicateChkDto){
        String msg = userService.nicknameDuplichk(duplicateChkDto);
        if(msg.equals("??????????????? ????????? ?????????.")){
            return ResponseEntity.status(HttpStatus.OK).body(commUtils.responseHashMap(HttpStatus.OK));
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(commUtils.responseHashMap(HttpStatus.BAD_REQUEST));
        }
    }

//     ?????? ?????? ?????? ??????
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

    //    ????????? ?????????
    @PostMapping("/auth/signUp/email-resend")
    public ResponseEntity<HashMap<String, String>> resendSignUpEmail(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "nickname") String nickname,
            @RequestParam(value = "profileImgUrl", required = false) MultipartFile multipartFile
    ) throws IOException {

        redisUtil.delete(username);
        String profileImgUrl = null;

        if(multipartFile!=null){
            profileImgUrl = s3Uploader.upload(multipartFile, "static");
        }
        SignupRequestDto signupRequestDto = new SignupRequestDto(username, password, nickname, profileImgUrl);
        return ResponseEntity.status(HttpStatus.OK).body(userService.registerUser(signupRequestDto));

    }


    //????????? ?????????
    @GetMapping("/auth/kakao/callback")
    public ResponseEntity<KakaoUserResponseDto> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(kakaoUserService.kakaoLogin(code));
    }

    //?????? ?????????
    @GetMapping("/auth/google/callback")
    public ResponseEntity<GoogleUserResponseDto> googleLogin(@RequestParam String code) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(googleUserService.googleLogin(code));
    }


    // ???????????? ????????? ?????? ?????????
    @PostMapping("/auth/password-reset-email")
    public ResponseEntity<CMResponseDto> sendPasswordResetLink(@RequestBody @Valid EmailRequestDto emailRequestDto) throws InvalidActivityException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.sendPasswordResetLink(emailRequestDto));

    }

    //??????????????? ?????? ????????? ????????????
    @PostMapping("/auth/password-reset-email/callback")
    public ResponseEntity<HashMap<String, String>> checkResetEmailToken(PasswordResetDto passwordResetDto) throws InvalidActivityException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkPasswordResetEmailToken(passwordResetDto));
    }

    //????????? ??????
    @GetMapping("/user/isLogIn")
    private ResponseEntity<IsLoginDto> isloginChk(@AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.isloginChk(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(userService.isloginChk(userDetails));
    }

    //??????????????? ?????? ????????? ????????????
    @GetMapping("/auth/check-email-token")
    public ResponseEntity<UserResponseDto> checkEmailToken(String token, String email, HttpServletResponse response) throws InvalidActivityException {
        UserResponseDto userResponseDto = userService.checkEmailToken(token, email);
            response.setHeader("Authorization", userResponseDto.getToken());
            response.setHeader("Access-Control-Allow-Origin", "https://chorok.kr");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept");
            return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);

    }

    // ?????? ?????? ?????????
    @PutMapping("/user/labeling")
    public ResponseEntity<LabelingResponseDto> labelingTest(@RequestBody LabelingDto labelingDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.updateLabeling(labelingDto, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(userService.getLabelingPlant(labelingDto));
    }

    // ???????????? ?????? ?????? ?????????
    @GetMapping("/auth/labeling")
    public ResponseEntity<LabelingResponseDto> NonLoginLabelingTest(
            @RequestParam String answer1, @RequestParam String answer2,
            @RequestParam String answer3, @RequestParam String answer4) {

        LabelingDto labelingDto = new LabelingDto(answer1, answer2, answer3, answer4);
        return ResponseEntity.status(HttpStatus.OK).body(userService.getLabelingPlant(labelingDto));
    }

    // ??????????????? ?????? ?????? ????????? ??????
    @GetMapping("/user/labeling/results")
    public ResponseEntity<List<LabelingResponseDto>> getLabelingResults(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getLabelingResults(userDetails));
    }

    // ?????? ????????? ????????????
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
    //CI/CD ??????
    @GetMapping("/auth") public String checkHealth() {
        return "?????????12";
    }
}

