package com.finalproject.chorok.login.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private String token;
    private Long userId;
    private String username;
    private String nickname;
    private String email;
}