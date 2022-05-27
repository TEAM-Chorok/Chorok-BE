package com.finalproject.chorok.login.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakaoUserResponseDto {
    private String token;
    private Long userId;
    private String nickname;
    private String email;
}