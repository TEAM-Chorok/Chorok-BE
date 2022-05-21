package com.finalproject.chorok.mypage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MypagePagingDto {
    private  List<?> content = new ArrayList<>();
    private  int pageSize;
    private  int page;
    private  int totalPage;

    public MypagePagingDto(Page<?> pageList) {
        this.content = pageList.getContent();
        this.pageSize = pageList.getSize();
        this.page = pageList.getPageable().getPageNumber();
        this.totalPage = pageList.getTotalPages();
    }
}
