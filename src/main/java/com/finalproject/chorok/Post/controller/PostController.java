package com.finalproject.chorok.Post.controller;

import com.finalproject.chorok.Post.dto.PostDetailResponseDto;
import com.finalproject.chorok.Post.dto.PostResponseDto;
import com.finalproject.chorok.Post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * [Controller] - 게시판 Controller
 *
 * @class   : PostController
 * @author  : 김주호
 * @since   : 2022.04.30
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 게시글 전체 조회 (게시글 타입으로 분류)
    @GetMapping("/read-posts/{postTypeCode}")
    public List<PostResponseDto> readPosts(@PathVariable String postTypeCode){
        return postService.readPosts(postTypeCode);
    }

    // 게시글 전체 조회 (게시글 타입과 식물위치로 분류)
//    @GetMapping("/read-posts/{postTypeCode}/{plantPlaceCode}")
//    public String readPlantPlacePosts(@PathVariable String postTypeCode, @PathVariable String plantPlaceCode){
//        postService.readPlantPlacePosts(postTypeCode,plantPlaceCode);
//        return "";
//    }

//    @GetMapping("/read-post/detail/{postId}")
//    public PostDetailResponseDto readPostDetail(@PathVariable Long postId){
//        postService.readPostDetail(postId);
//
//    }
}
