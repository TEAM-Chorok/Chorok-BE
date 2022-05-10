package com.finalproject.chorok.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GoogleUserInfoDto {
    String googleId;
    String email;
    String nickname;
    String profileImage;
}