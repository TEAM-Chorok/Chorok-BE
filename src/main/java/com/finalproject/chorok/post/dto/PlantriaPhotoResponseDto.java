package com.finalproject.chorok.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PlantriaPhotoResponseDto {
    private int plantriaCount;
    private List<PostResponseDto> postList;

    public PlantriaPhotoResponseDto(int plantriaCount, List<PostResponseDto> postList){
        this.plantriaCount=plantriaCount;
        this.postList=postList;

    }
}
