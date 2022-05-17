package com.finalproject.chorok.post.controller;

import com.finalproject.chorok.post.dto.DictionaryFilterDto;
import com.finalproject.chorok.post.dto.PlantriaFilterRequestDto;
import com.finalproject.chorok.post.dto.PostRequestDto;
import com.finalproject.chorok.post.dto.PostWriteRequestDto;
import com.finalproject.chorok.post.repository.PostRepository;
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
    private final PostRepository postRepository;


    /*
     * 플랜테리어 조회(queryParameter로 필터링)
     * @Example : read-posts?postTypecode = postType01
     */
    @GetMapping("/read-posts")
    public ResponseEntity<?> readPosts(PlantriaFilterRequestDto postSearchRequestDto){

        return ResponseEntity.status(HttpStatus.OK).body(postRepository.plantriaReadPosts(postSearchRequestDto));
    }
    /*
     * 플렌테리어 통합 검색
     * @Example : /search-post/planterior?keyword=
     */
    @GetMapping("/search-post/integrate/planterior")
    public ResponseEntity<?> integrateSearchPlanterior(PlantriaFilterRequestDto postSearchRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(postService.integrateSearchPlanterior(postSearchRequestDto));
    }
    /*
     * 플렌테리어 통합 검색 - 사진
     */
    @GetMapping("/search-post/photo/planterior")
    public ResponseEntity<?> photoSearchPlanterior(PlantriaFilterRequestDto postSearchRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.photoSearchPlanterior(postSearchRequestDto));
    }
    /*
     * 플렌테리어 통합 검색 - 식물도감
     * 식물도감 조회
     */
    @GetMapping("/search-post/dictionary/planterior")
    public ResponseEntity<?> dictionarySearchPlanterior(DictionaryFilterDto dictionaryFilterDto) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.dictionarySearchPlantria(dictionaryFilterDto));
    }

    /*
     * 초록톡 전체 조회 (로그인 시)
     * @Example : read-posts/community?postTypeCode=
     */
    @GetMapping("/read-posts/community")
    public ResponseEntity<?> readPostsCommunity(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(name = "postTypeCode",required = false) String postTypeCode,
            @RequestParam(name="keyword", required = false)  String keyword
    )  {

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
    @GetMapping("like-post/{postId}")
    public ResponseEntity<?> likePost(@Valid @PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return ResponseEntity.status(HttpStatus.OK).body(postService.likePost(postId,userDetails.getUser()));

    }

    // 게시글 북마크 기능
    @GetMapping("bookmark-post/{postId}")
    public ResponseEntity<?> bookMarkPost(@Valid @PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return ResponseEntity.status(HttpStatus.OK).body(postService.bookMarkPost(postId,userDetails.getUser()));
    }

}

