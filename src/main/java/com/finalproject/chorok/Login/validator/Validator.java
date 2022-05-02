package com.finalproject.chorok.Login.validator;


import com.finalproject.chorok.Login.dto.DuplicateChkDto;
import com.finalproject.chorok.Login.dto.SignupRequestDto;
import com.finalproject.chorok.Login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;


@Component
@RequiredArgsConstructor
public class Validator {
    private final UserRepository userRepository;


    // 회원가입 유효성 검사
    public void signupValidate(SignupRequestDto signupRequestDto) throws IllegalArgumentException {
        if (userRepository.findByUsername(signupRequestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
        }

        if (userRepository.findByNickname(signupRequestDto.getNickname()).isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임이 존재합니다.");
        }

        if(!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", signupRequestDto.getUsername())){
            throw new IllegalArgumentException("이메일 형식의 ID를 입력 해주세요.");
        }

        if(signupRequestDto.getPassword().length()<4){
            throw new IllegalArgumentException("비밀번호는 최소 4자 이상이여야 합니다.");
        }

        if(signupRequestDto.getPassword().contains(signupRequestDto.getUsername())){
            throw new IllegalArgumentException("이메일이 포함되지 않은 비밀번호를 사용해주세요.");
        }
    }

    //아이디 중복 체크
    public void idCheck(DuplicateChkDto duplicateChkDto) throws IllegalArgumentException {
        if (userRepository.findByUsername(duplicateChkDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
        }
    }

    //닉네임 중복 체크
    public void nickCheck(DuplicateChkDto duplicateChkDto) throws IllegalArgumentException {
        if (userRepository.findByNickname(duplicateChkDto.getNickname()).isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임이 존재합니다.");
        }
    }




}

//
//    // 회원가입 시, 유효성 체크
//    public Map<String, String> validateHandling(Errors errors) {
//        Map<String, String> validatorResult = new HashMap<>();
//
//        for (FieldError error : errors.getFieldErrors()) {
//            String validKeyName = "message";
//            validatorResult.put(validKeyName, error.getDefaultMessage());
//        }
//        return validatorResult;
//    }
//
//    public Page<PostListDto> overPages(List<PostListDto> postList, int start, int end, Pageable pageable, int page) {
//        Page<PostListDto> pages = new PageImpl<>(postList.subList(start, end), pageable, postList.size());
//        if(page > pages.getTotalPages()){
//            throw new IllegalArgumentException("요청할 수 없는 페이지 입니다.");
//        }
//        return pages;
//    }


//}