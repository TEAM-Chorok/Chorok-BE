package com.finalproject.chorok.login.service;


import com.finalproject.chorok.common.utils.PlantUtils;
import com.finalproject.chorok.common.utils.RedisUtil;
import com.finalproject.chorok.login.dto.*;

import com.finalproject.chorok.login.model.EmailMessage;
import com.finalproject.chorok.login.model.Labeling;
import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.login.repository.EmailService;
import com.finalproject.chorok.login.repository.LabelingRepository;
import com.finalproject.chorok.login.repository.UserRepository;
import com.finalproject.chorok.login.validator.Validator;
import com.finalproject.chorok.plant.model.Plant;
import com.finalproject.chorok.plant.repository.PlantRepository;
import com.finalproject.chorok.login.dto.LabelingResponseDto;
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
    private final LabelingRepository labelingRepository;
    private final RedisUtil redisUtil;
    private final PlantRepository plantRepository;
    private final PlantUtils plantUtils;

    @Transactional
    public String registerUser(SignupRequestDto requestDto) {
//        String msg = "회원가입 성공";
        String msg = "회원인증 이메일 전송";

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
        String profileImgUrl = requestDto.getProfileImgUrl();

        User user = new User(username, password, nickname, emailCheckToken, profileImgUrl);

//         //이메일 인증 코드부분
//        redisUtil.set(emailCheckToken, user, 2);
//
//        System.out.println(user+"4");
//
//        sendSignupConfirmEmail(user);

        // 이메일 인증 생략하고 회원가입(추후 삭제)
        User savedUser = userRepository.save(user);
        Labeling defaultLabeling = new Labeling(savedUser);
        labelingRepository.save(defaultLabeling);
        return msg;
    }

    public void labelingTest(UserDetailsImpl userDetails) {

        Labeling defaultLabeling = new Labeling(userDetails.getUser());
        labelingRepository.save(defaultLabeling);
    }



        @Transactional
    public ResponseEntity<CMResponseDto> sendTempPassword(EmailRequestDto emailRequestDto) throws InvalidActivityException {

        User findUser = userRepository.findByUsername(emailRequestDto.getEmail()).orElseThrow(
                () -> new InvalidActivityException("존재하지 않는 이메일입니다.")
        );
        System.out.println("이메일 존재여부 체크");
         //인증 이메일 1시간 지났는지 체크

        String tempPassword = temporaryPassword(10); // 8글자 랜덤으로 임시 비밀번호 생성

        String tempEncPassword = passwordEncoder.encode(tempPassword); // 암호화
        System.out.println("암호화"+tempEncPassword);
        findUser.changePassword(tempEncPassword);

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

        User savedUser = userRepository.save(findUser);
        Labeling defaultLabeling = new Labeling(savedUser);
        labelingRepository.save(defaultLabeling);
        System.out.println("User 저장");
        if(savedUser.getUserId() > 0) redisUtil.delete(token);
        System.out.println("redisutil 제거");

        return ResponseEntity.ok(new CMResponseDto("true"));
    }

    @Transactional
    public String updateLabeling(LabelingDto labelingDto, UserDetailsImpl userDetails) {
        String msg = "레이블링 업데이트 성공";
        Labeling labeling = labelingRepository.findByUser(userDetails.getUser()).orElseThrow(
                () -> new IllegalArgumentException("레이블링 오류입니다.")
        );
        labeling.update(labelingDto);
        System.out.println("레이블링 업데이트 성공");
        return msg;
    }

    @Transactional
    public LabelingResponseDto getLabelingPlant(LabelingDto labelingDto) {
        System.out.println("식물 검색하기 까지 들어옴");
        Plant labeledPlant = plantRepository.searchOnePlantByLabeling(labelingDto.getAnswer1(), labelingDto.getAnswer2(), labelingDto.getAnswer3(), labelingDto.getAnswer4());

        return new LabelingResponseDto(
                labeledPlant.getPlantNo(),
                plantUtils.getPlantThumbImg(labeledPlant.getPlantNo()),
                labeledPlant.getPlantName()
        );
    }

    @Transactional
    public List<LabelingResponseDto> getLabelingResults(UserDetailsImpl userDetails) {
        System.out.println("서비스 단으로 넘어오나??");
//        Long temp = 6L;
//        Optional<Labeling> labelingTested = labelingRepository.findByUserUserId(temp);
        System.out.println(userDetails.getUser().getUserId());
        System.out.println(userDetails.getUserId());
        Optional<Labeling> labelingTested = labelingRepository.findByUser_UserId(userDetails.getUserId());
        if(labelingTested.isPresent()){
        List<Plant> labeledPlants = plantRepository.searchThreePlantByLabeling(
                labelingTested.get().getAnswer1(),
                labelingTested.get().getAnswer2(),
                labelingTested.get().getAnswer3(),
                labelingTested.get().getAnswer4());

        List<LabelingResponseDto> labelingResponseDtos = new ArrayList<>();

        for (Plant labeledPlant : labeledPlants) {

            LabelingResponseDto labelingResponseDto = new LabelingResponseDto(
                    labeledPlant.getPlantNo(),
                    plantUtils.getPlantThumbImg(labeledPlant.getPlantNo()),
                    labeledPlant.getPlantName()
            );
            labelingResponseDtos.add(labelingResponseDto);

        }
        return labelingResponseDtos;}
        else {
            return null;
        }
    }
}