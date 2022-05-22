package com.finalproject.chorok.mypage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class MypageMyBookMarkplantFinalDto {
    int count;
    List<?> myBookMarkPlant;

    public MypageMyBookMarkplantFinalDto(Page<?> myBookMarkPlant) {
        count = (int) myBookMarkPlant.getTotalElements();
        this.myBookMarkPlant = myBookMarkPlant.getContent();
    }
}
