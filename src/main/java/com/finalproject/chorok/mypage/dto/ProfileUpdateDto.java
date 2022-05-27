package com.finalproject.chorok.mypage.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateDto {
    private String nickname;
    private String profileMsg;
    private String password;
}
