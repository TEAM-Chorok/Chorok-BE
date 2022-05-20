package com.finalproject.chorok.login.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateChkDto {
    private String username;
    private String nickname;

    public DuplicateChkDto(String nickname) {
        this.nickname = nickname;
    }
}
