package com.finalproject.chorok.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import software.amazon.ion.Decimal;

@Getter
@Builder
@AllArgsConstructor
public class GoogleUserInfoDto {
    Decimal googleId;
    String email;
    String nickname;
    String profileImage;
}