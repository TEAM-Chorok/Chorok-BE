package com.finalproject.chorok.Login.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IsLoginDto {
    private Long userId;
    private String username;
    private String nickname;

}
