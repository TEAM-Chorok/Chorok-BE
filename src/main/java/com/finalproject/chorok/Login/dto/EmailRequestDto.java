package com.finalproject.chorok.Login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
}