package com.finalproject.chorok.login.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor
public class SignupRequestDto {
    private String username;
    private String password;
    private String nickname;
    private String profileImgUrl;

    public SignupRequestDto(String username, String password, String nickname, String profileImgUrl) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }
}