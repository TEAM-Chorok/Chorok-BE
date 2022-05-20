package com.finalproject.chorok.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostPagingDto {

    private List<?> content = new ArrayList<>();
    private  int contentCount;
    private  int pageSize;
    private  int page;
    private  int totalPage;

    public PostPagingDto(Page<?> pageList) {
        this.contentCount = (int) pageList.getTotalElements();
        this.content = pageList.getContent();
        this.pageSize = pageList.getSize();
        this.page = pageList.getPageable().getPageNumber();
        this.totalPage = pageList.getTotalPages();

    }
}
