package com.finalproject.chorok.Common.Image.test;



import com.finalproject.chorok.Common.Image.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@ResponseStatus(HttpStatus.OK)
public class PostController {

    private final PostService postService;
    private final S3Uploader S3Uploader;



    // 게시글 작성
    @PostMapping("/api/write")
    public ResponseEntity<String> upload(
            @RequestParam("postTitle") String postTitle,
            @RequestParam("postContents") String postContents,
            @RequestParam(value = "imageUrl") MultipartFile multipartFile,
            @RequestParam("price") int price,
            @RequestParam("category") String category
    ) throws IOException
    {
        String imageUrl = S3Uploader.upload(multipartFile, "static");

        PostRequestDto postRequestDto = new PostRequestDto(postTitle, postContents, imageUrl, price, category);
        postService.createPost(postRequestDto);
        return ResponseEntity.status(201)
                .header("status","201")
                .body("201");
}

//
//    // 전체 게시글 조회, 페이징 처리 완료
//    @GetMapping("/api/posted/{pageno}")
//    public PostsResponseDto showAllPost(@PathVariable("pageno") int pageno, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return new PostsResponseDto(postService.showAllPost(pageno-1, userDetails));
//    }
//
//
//    // 특정 게시글 조히
//    @GetMapping("/api/posts/{postId}")
//    public ResponseEntity<PostDetailResponseDto> getPostDetail(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return ResponseEntity.status(201)
//                .header("status","201")
//                .body(postService.getPostDetail(postId, userDetails));
//    }

    // 게시글 수정
//    @PutMapping("/api/posts/{postId}")
//    public ResponseEntity<String> editPost(@PathVariable Long postId,
//                                                    @RequestParam(value = "postTitle",required = false) String postTitle,
//                                                    @RequestParam(value = "postContents",required = false) String postContents,
//                                                    @RequestParam(value = "imageUrl", required = false) MultipartFile multipartFile,
//                                                    @RequestParam(value = "price", required = false) int price,
//                                                    @RequestParam(value = "category",required = false) String category,
//                                                    @AuthenticationPrincipal UserDetailsImpl userDetails)
//    throws IOException{
//
//        if(multipartFile.isEmpty()){
//
//            PostRequestDto postRequestDto = new PostRequestDto(postTitle, postContents,  price, category);
//            postService.editPost(postId,postRequestDto, userDetails.getUser());
//        }else{
//            String imageUrl = S3Uploader.updateImage(multipartFile, "static", postId);
//
//            PostRequestDto postRequestDto = new PostRequestDto(postTitle, postContents, imageUrl, price, category);
//            postService.editPost(postId,postRequestDto, userDetails.getUser());
//        }
//
//        return new ResponseEntity<String>(HttpStatus.OK);
//    }


    // 게시글 삭제
//    @DeleteMapping("api/posts/{postId}")
//    public ResponseEntity<StatusMessage> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        StatusMessage statusMessage = new StatusMessage();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//        statusMessage.setStatus(StatusEnum.OK);
//        statusMessage.setData(postService.deletePost(postId, userDetails.getUser()));
//        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
//    }

}
