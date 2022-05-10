package com.finalproject.chorok.post.controller;

import com.finalproject.chorok.post.dto.PostRequestDto;
import com.finalproject.chorok.post.dto.PostWriteRequestDto;
import com.finalproject.chorok.post.service.PostService;
import com.finalproject.chorok.post.utils.CommUtils;
import com.finalproject.chorok.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    private final CommUtils commUtils;

    // 게시글 전체 조회 (게시글 타입으로 분류)
    @GetMapping("/read-posts/{postTypeCode}")
    public ResponseEntity<?> readPosts( @Valid @PathVariable String postTypeCode){

        return ResponseEntity.status(HttpStatus.OK).body(postService.readPosts(postTypeCode));
    }

    // 게시글 전체 조회 (게시글 타입과 식물위치로 분류)
    @GetMapping("/read-posts/{postTypeCode}/{plantPlaceCode}")
    public ResponseEntity<?> readPlantPlacePosts(@Valid @PathVariable String postTypeCode, @Valid @PathVariable String plantPlaceCode){

        return ResponseEntity.status(HttpStatus.OK).body(postService.readPlantPlacePosts(postTypeCode,plantPlaceCode));
    }
    /**
     *
     * @param postId
     * @return
     */
    @GetMapping("/read-post/detail/{postId}")
    public ResponseEntity<?> readPostDetail(@Valid @PathVariable Long postId,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.OK).body(postService.readPostDetail(postId,userDetails.getUser()));
    }

    // 게시글 작성
    @PostMapping("/write-post")
    public ResponseEntity<?> writePost(@Valid @RequestBody PostWriteRequestDto postWriteRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.OK).body(commUtils.responseHashMap(HttpStatus.OK));
    }

    // 게시글 삭제
    @DeleteMapping("/delete-post/{postId}")
    public ResponseEntity<?> deletePost(@Valid @PathVariable Long postId){
        postService.deletePost(postId);
        return  ResponseEntity.status(HttpStatus.OK).body(commUtils.responseHashMap(HttpStatus.OK));

    }

    // 게시글 수정
    @PutMapping("/update-post/{postId}")
    public ResponseEntity<?> updatePost(@Valid @PathVariable Long postId, @Valid PostRequestDto postRequestDto){
        postService.updatePost(postId,postRequestDto);

        return  ResponseEntity.status(HttpStatus.OK).body(commUtils.responseHashMap(HttpStatus.OK));
    }

    // 게시글 좋아요 기능
    @PostMapping("like-post/{postId}")
    public ResponseEntity<?> likePost(@Valid @PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return ResponseEntity.status(HttpStatus.OK).body(postService.likePost(postId,userDetails.getUser()));

    }
    // 게시글 북마크 기능
    @PostMapping("bookmark-post/{postId}")
    public ResponseEntity<?> bookMarkPost(@Valid @PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return ResponseEntity.status(HttpStatus.OK).body(postService.bookMarkPost(postId,userDetails.getUser()));

    }

    // 커뮤니티 게시판 전체조회

    // 커뮤니티 게시판 필터조회



}
