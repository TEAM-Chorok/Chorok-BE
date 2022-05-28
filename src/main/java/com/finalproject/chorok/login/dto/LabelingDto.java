package com.finalproject.chorok.login.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelingDto {
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;

    public LabelingDto(String answer1, String answer2, String answer3, String answer4) {
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
    }
}
