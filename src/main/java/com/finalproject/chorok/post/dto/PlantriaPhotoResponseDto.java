package com.finalproject.chorok.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class PlantriaPhotoResponseDto {
    private int plantriaCount;
    private List<PostResponseDto> postList;

    public PlantriaPhotoResponseDto(int plantriaCount, Page<PostResponseDto> postList){
        this.plantriaCount=plantriaCount;
        this.postList= (List<PostResponseDto>) postList;

    }
}
