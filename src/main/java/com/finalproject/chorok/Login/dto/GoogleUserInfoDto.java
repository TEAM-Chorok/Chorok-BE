package com.finalproject.chorok.Login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GoogleUserInfoDto {
    String username;
    String nickname;
}