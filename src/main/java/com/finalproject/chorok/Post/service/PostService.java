package com.finalproject.chorok.Post.service;

import com.finalproject.chorok.Post.dto.PostRespoonseDto;
import com.finalproject.chorok.Post.model.Post;
import com.finalproject.chorok.Post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * [Service] - 게시판 Service
 *
 * @class : PostService
 * @since : 2022.04.30
 * @author : 김주호
 * @version : 1.0
 *
 * 수정일     수정자             수정내용
 * --------   --------    ---------------------------
 *
 */
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    // 전체 게시물 조회
    public List<PostRespoonseDto> readPosts(String postTypeCode) {

        List<Post> postList = postRepository.findAllByPostTypePostTypeCodeOrderByCreatedAt(postTypeCode);
        List<PostRespoonseDto> postRespoonseDtoList = new ArrayList<>();
        PostRespoonseDto postRespoonseDto = null;

        for(Post post : postList){
             postRespoonseDto = new PostRespoonseDto(post.getPostId(),post.getPostTitle(),post.getPostImgUrlNo());
             postRespoonseDtoList.add(postRespoonseDto);
        }

        return postRespoonseDtoList;

    }
}
