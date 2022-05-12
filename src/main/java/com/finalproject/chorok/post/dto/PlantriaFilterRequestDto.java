package com.finalproject.chorok.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Setter
public class PlantriaFilterRequestDto {
    @NotBlank(message = "게시물 타입은 필수값입니다.")
    @NotNull(message = "게시물 타입은 필수값입니다.")
    private String postTypeCode;

    private String plantPlaceCode;

    private String keyword;
}
