package com.finalproject.chorok.post.controller;

import com.finalproject.chorok.post.dto.PlantriaFilterRequestDto;
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


    /*
     * 플랜테리어 조회(queryParameter로 필터링)
     * @Example : read-posts?postTypecode = postType01
     */
    @GetMapping("/read-posts")
    public ResponseEntity<?> readPosts(@Valid PlantriaFilterRequestDto postSearchRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(postService.readPosts(postSearchRequestDto));
    }

    /*
     * 초록톡 전체 조회 (로그인 시)
     * @Example : read-posts/community?postTypeCode=
     */
    @GetMapping("/read-posts/community")
    public ResponseEntity<?> readPostsCommunity(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(name = "postTypeCode",required = false) String postTypeCode) {

        return ResponseEntity.status(HttpStatus.OK).body(postService.readPostsCommunity(userDetails.getUser(),postTypeCode));
    }

    /*
     * 초록톡 전체 조회 (비 로그인 시)
     */
    @GetMapping("/non-login/read-posts/community")
    public ResponseEntity<?> nonLoginReadPostsCommunity(@RequestParam(name = "postTypeCode",required = false) String postTypeCode) {

        return ResponseEntity.status(HttpStatus.OK).body(postService.nonLoginReadPostsCommunity(postTypeCode));
    }

    /*
     * 플렌테리어, 초록톡 -  상세조회
     */
    @GetMapping("/read-post/detail/{postId}")
    public ResponseEntity<?> readPostDetail(@Valid @PathVariable Long postId,@AuthenticationPrincipal UserDetailsImpl userDetails){

        return ResponseEntity.status(HttpStatus.OK).body(postService.readPostDetail(postId,userDetails.getUser()));
    }


    /*
     * 플렌테리어 통합 검색
     * @Example : /search-post/plantria?keyword=
     */
    @GetMapping("/search-post/integrate/plantria")
    public ResponseEntity<?> integrateSearchPlantria(@RequestParam(name = "keyword",required = false) String keyword){

        return ResponseEntity.status(HttpStatus.OK).body(postService.integrateSearchPlantria(keyword));
    }
    /*
     * 플렌테리어 통합 검색 - 사진
     */
    @GetMapping("/search-post/photo/plantria")
    public ResponseEntity<?> photoSearchPlantria(@RequestParam(name = "keyword",required = false) String keyword,
                                                 @RequestParam(name = "plantPlaceCode",required = false) String plantPlaceCode
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(postService.photoSearchPlantria(keyword,plantPlaceCode));
    }
    /*
     * 플렌테리어 통합 검색 - 식물도감
     */
    @GetMapping("/search-post/dictionary/plantria")
    public ResponseEntity<?> dictionarySearchPlantria(@RequestParam(name = "keyword",required = false) String keyword) {

        return ResponseEntity.status(HttpStatus.OK).body(postService.dictionarySearchPlantria(keyword));
    }

    // 게시글 작성
    @PostMapping("/write-post")
    public ResponseEntity<?> writePost(@Valid @RequestBody PostWriteRequestDto postWriteRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return ResponseEntity.status(HttpStatus.OK).body( postService.writePost(postWriteRequestDto,userDetails.getUser()));
    }

    // 게시글 삭제
    @DeleteMapping("/delete-post/{postId}")
    public ResponseEntity<?> deletePost(@Valid @PathVariable Long postId){

        postService.deletePost(postId);
        return  ResponseEntity.status(HttpStatus.OK).body(postService.deletePost(postId));
    }

    // 게시글 수정
    @PutMapping("/update-post/{postId}")
    public ResponseEntity<?> updatePost(@Valid @PathVariable Long postId, @Valid PostRequestDto postRequestDto){

        return  ResponseEntity.status(HttpStatus.OK).body( postService.updatePost(postId,postRequestDto));
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

}

