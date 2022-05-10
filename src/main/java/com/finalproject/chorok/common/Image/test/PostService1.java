package com.finalproject.chorok.common.Image.test;


import com.amazonaws.services.s3.AmazonS3Client;

import com.finalproject.chorok.common.Image.ImageRepository;

import com.finalproject.chorok.common.Image.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;



@Slf4j
@Component
@RequiredArgsConstructor
@Service
public class PostService1 {

    private final com.finalproject.chorok.common.Image.test.PostRepository1 postRepository;
    private final ImageRepository imageRepository;
    private final AmazonS3Client amazonS3Client;
    private final S3Uploader s3Uploader;

//    @Autowired
//    public PostService(PostRepository postRepository) {
//        this.postRepository = postRepository;
//    };

    // 게시물 등록
    public com.finalproject.chorok.common.Image.test.PostResponseDto1 createPost(com.finalproject.chorok.common.Image.test.PostRequestDto1 postRequestDto) {

        com.finalproject.chorok.common.Image.test.Post1 post = com.finalproject.chorok.common.Image.test.Post1.builder()

                        .postTitle(postRequestDto.getPostTitle())
                        .postContents(postRequestDto.getPostContents())
                        .imageUrl(postRequestDto.getImageUrl())
                        .price(postRequestDto.getPrice())
                        .category(postRequestDto.getCategory())
                        .build();

        postRepository.save(post);

        return com.finalproject.chorok.common.Image.test.PostResponseDto1.builder()

                .build();
    }

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름


    // 게시글 삭제
//    @Transactional
//    public Object deletePost(Long postId, User user) {
//
//        Post post = postRepository.findByIdAndUserId(postId,user.getId()).orElseThrow(
//                () -> new IllegalArgumentException("작성자만 삭제 가능합니다.")
//        );
//
////         S3 이미지 삭제
//        String temp = post.getImageUrl();
//        Image image = imageRepository.findByImageUrl(temp);
//        String fileName = image.getFilename();
//        s3Uploader.deleteImage(fileName);
//
//        postLikeRepository.deleteAllByPostId(postId);
//        postRepository.deleteById(post.getId());
//
//        return null;
//    }

}

