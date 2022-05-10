package com.finalproject.chorok.todo.dto;

import com.finalproject.chorok.security.UserDetailsImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
public class TodoResponseDto {
    private Long myPlantNo;
    private String myPlantName;
    private String myPlantImgUrl;
    private String myPlantPlace;
    private List<TodoOnlyResponseDto> todoOnlys;

    public TodoResponseDto( Long myPlantNo, String myPlantName, String myPlantImgUrl, String myPlantPlace, List<TodoOnlyResponseDto> todoOnlys) {
        this.myPlantNo = myPlantNo;
        this.myPlantName = myPlantName;
        this.myPlantImgUrl = myPlantImgUrl;
        this.myPlantPlace = myPlantPlace;
        this.todoOnlys = todoOnlys;

    }
    public TodoResponseDto(TodoResponseDto todoResponseDto, UserDetailsImpl userDetails) {
        this.myPlantNo = todoResponseDto.myPlantNo;
        this.myPlantName = todoResponseDto.myPlantName;
        this.myPlantImgUrl = todoResponseDto.myPlantImgUrl;
        this.myPlantPlace = todoResponseDto.myPlantPlace;
        this.todoOnlys = todoResponseDto.todoOnlys;

    }

}
