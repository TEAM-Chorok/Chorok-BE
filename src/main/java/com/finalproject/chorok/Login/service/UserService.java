package com.finalproject.chorok.Login.service;


import com.finalproject.chorok.Common.utils.RedisUtil;
import com.finalproject.chorok.Login.dto.*;

import com.finalproject.chorok.Login.model.EmailMessage;
import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.Login.repository.EmailService;
import com.finalproject.chorok.Login.repository.UserRepository;
import com.finalproject.chorok.Login.validator.Validator;
import com.finalproject.chorok.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.activity.InvalidActivityException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final TemplateEngine templateEngine;
    private final HttpServletRequest request;
    private final Validator validator;
    private final RedisUtil redisUtil;


    @Transactional
    public String registerUser(SignupRequestDto requestDto) {
        String msg = "회원인증 이메일 발송";

        try {
            //회원가입 확인
            validator.signupValidate(requestDto);
        } catch (IllegalArgumentException e) {
            msg = e.getMessage();
            return msg;
        }

        // 패스워드 암호화
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();
        System.out.println(password+"3");

        String emailCheckToken = UUID.randomUUID().toString();
//        LocalDateTime emailCheckTokenGeneratedAt = LocalDateTime.now();

        User user = new User(username, password, nickname, emailCheckToken);

        redisUtil.set(emailCheckToken, user, 2);
//        User savedUser = userRepository.save(user);
        System.out.println(user+"4");

        sendSignupConfirmEmail(user);
        return msg;
    }



    @Transactional
    public ResponseEntity<CMResponseDto> sendTempPassword(EmailRequestDto emailRequestDto) throws InvalidActivityException {

        User findUser = userRepository.findByUsername(emailRequestDto.getEmail()).orElseThrow(
                () -> new InvalidActivityException("존재하지 않는 이메일입니다.")
        );
        System.out.println("이메일 존재여부 체크");
         //인증 이메일 1시간 지났는지 체크
//        if (!findUser.canSendConfirmEmail())
//            throw new InvalidActivityException("인증 이메일은 1시간에 한번만 전송할 수 있습니다.");
//        System.out.println("인증이메일 1시간 체크");
        String tempPassword = temporaryPassword(10); // 8글자 랜덤으로 임시 비밀번호 생성

        String tempEncPassword = passwordEncoder.encode(tempPassword); // 암호화
        System.out.println("암호화"+tempEncPassword);
        findUser.changeTempPassword(tempEncPassword);

        sendTempPasswordConfirmEmail(findUser, tempPassword);
        System.out.println("작업완료");
        return ResponseEntity.ok(new CMResponseDto("true"));
    }
//
    private void sendTempPasswordConfirmEmail(User user, String tempPwd) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(user.getUsername())
                .subject("초록(Chorok), 임시 비밀번호 발급")
                .message("<p>임시 비밀번호: <b>" + tempPwd + "</b></p><br>" +
                        "<p>로그인 후 비밀번호를 변경해주세요.</p>")
                .build();
        System.out.println("sendTempPasswordConfirmEmail");
        System.out.println(emailMessage);
        emailService.sendEmail(emailMessage);
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        user.setUpdatedAt(now);
        System.out.println("sendEmail");
    }



    private String temporaryPassword(int size) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        String chars[] = ("A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z," +
                "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,0,1,2,3,4,5,6,7,8,9").split(",");
        for (int i = 0; i < size; i++) {
            buffer.append(chars[random.nextInt(chars.length)]);
        }
        buffer.append("!a1");
        System.out.println("임시비밀번호 생성"+buffer.toString());
        return buffer.toString();
    }


    private void sendSignupConfirmEmail(User user) {
        System.out.println("sendSignupConfirmEmail 시작");
        String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        Context context = new Context();
        context.setVariable("link", path+"/auth/check-email-token?token=" + user.getEmailCheckToken() +
                "&email=" + user.getUsername());
        System.out.println("진행체크1");
        String message = templateEngine.process("email-link", context);
        System.out.println("진행체크2");
        EmailMessage emailMessage = EmailMessage.builder()
                .to(user.getUsername())
                .subject("초록(Chorok), 회원 가입 인증 메일")
                .message(message)
                .build();
        System.out.println("진행체크3");
        emailService.sendEmail(emailMessage);
        System.out.println("진행체크4");
    }

    //로그인 확인
    public IsLoginDto isloginChk(UserDetailsImpl userDetails){
        System.out.println("isloginChk함수 들어옴");
        String username = userDetails.getUsername();
        String nickname = userDetails.getUser().getNickname();
        Long userId = userDetails.getUser().getUserId();
        Optional<User> user = userRepository.findByUsername(username);
        IsLoginDto isLoginDto = IsLoginDto.builder()
                .username(username)
                .nickname(nickname)
                .userId(userId)
                .build();
        System.out.println("isLoginDto 만들어짐");
        return isLoginDto;
    }

    //아이디 중복체크
    public String usernameDuplichk(DuplicateChkDto duplicateChkDto){
        String msg = "사용가능한 이메일 입니다.";

        try {
            validator.idCheck(duplicateChkDto);
        } catch (IllegalArgumentException e) {
            msg = e.getMessage();
            return msg;
        }
        return msg;
        }



    //닉네임 중복검사
    public String nicknameDuplichk(DuplicateChkDto duplicateChkDto) {
        String msg = "사용가능한 닉네임 입니다.";

        try {
            validator.nickCheck(duplicateChkDto);
        } catch (IllegalArgumentException e) {
            msg = e.getMessage();
            return msg;
        }
        return msg;
    }

    @Transactional
    public ResponseEntity<CMResponseDto> checkEmailToken(String token, String email) throws InvalidActivityException {
        System.out.println("이메일 토큰 인증과정 함수시작");

        User findUser = (User)redisUtil.get(token);

//        User findUser = userRepository.findByUsername(email).orElseThrow(
//                () -> new InvalidActivityException("존재하지 않는 이메일입니다.")
//        );
        if (!findUser.isValidToken(token))
            throw new InvalidActivityException("유효하지 않는 토큰입니다.");

        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        findUser.setCreatedAt(now);
        findUser.setUpdatedAt(now);

        User savedUser = userRepository.save(findUser);
        System.out.println("User 저장");
        if(savedUser.getUserId() > 0) redisUtil.delete(token);
        System.out.println("redisutil 제거");

        return ResponseEntity.ok(new CMResponseDto("true"));
    }
}