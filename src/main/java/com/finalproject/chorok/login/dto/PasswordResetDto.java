package com.finalproject.chorok.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PasswordResetDto {
    String token;
    String email;
    String newPassword;

}
