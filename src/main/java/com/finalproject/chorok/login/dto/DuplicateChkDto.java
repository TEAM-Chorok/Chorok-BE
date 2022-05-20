package com.finalproject.chorok.login.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DuplicateChkDto {
    private String username;
    private String nickname;

    public DuplicateChkDto(String nickname) {
        this.nickname = nickname;
    }
}
