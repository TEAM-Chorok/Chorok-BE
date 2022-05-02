package com.finalproject.chorok.Post.controller;

import com.finalproject.chorok.Post.dto.PostDetailResponseDto;
import com.finalproject.chorok.Post.dto.PostResponseDto;
import com.finalproject.chorok.Post.dto.PostWriteRequestDto;
import com.finalproject.chorok.Post.model.Post;
import com.finalproject.chorok.Post.service.PostService;
import com.finalproject.chorok.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/read-posts/{postTypeCode}/{plantPlaceCode}")
    public List<PostResponseDto> readPlantPlacePosts(@PathVariable String postTypeCode, @PathVariable String plantPlaceCode){

        return postService.readPlantPlacePosts(postTypeCode,plantPlaceCode);

    }
    // 상세페이지 조회
    @GetMapping("/read-post/detail/{postId}")
    public String readPostDetail(@PathVariable Long postId){
        postService.readPostDetail(postId);
        return "";
    }

    // 게시글 작성
    @PostMapping("/write-post")
    public Post writePost(@RequestBody PostWriteRequestDto postWriteRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
       return postService.writePost(postWriteRequestDto,userDetails.getUser());
    }

    // 게시글 삭제
    @DeleteMapping("/delete-post/{postId}")
    public String deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return "삭제완료";
    }

}
