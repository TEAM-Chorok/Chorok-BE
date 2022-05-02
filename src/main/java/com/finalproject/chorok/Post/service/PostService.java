package com.finalproject.chorok.Post.service;

import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.Post.dto.PostResponseDto;
import com.finalproject.chorok.Post.dto.PostWriteRequestDto;
import com.finalproject.chorok.Post.model.Post;
import com.finalproject.chorok.Post.model.PostType;
import com.finalproject.chorok.Post.repository.PostRepository;
import com.finalproject.chorok.Post.utils.CommUtils;
import com.finalproject.chorok.plant.model.PlantPlace;
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
    private final CommUtils commUtils;

    // 전체 게시물 조회
    public List<PostResponseDto> readPosts(String postTypeCode) {

        List<Post> postList = postRepository.findAllByPostTypePostTypeCodeOrderByCreatedAt(postTypeCode);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        PostResponseDto postResponseDto = null;

        for(Post post : postList){
            postResponseDto = new PostResponseDto(post.getPostId(),post.getPostTitle(),post.getPostImgUrl());
            postResponseDtoList.add(postResponseDto);
        }

        return postResponseDtoList;

    }

     //게시글 전체 조회 (게시글 타입과 식물위치로 분류)
    public List<PostResponseDto> readPlantPlacePosts(String postTypeCode, String plantPlaceCode) {
        List<Post> postList = postRepository.findAllByPostTypePostTypeCodeAndPlantPlacePlantPlaceCodeOrderByCreatedAt(postTypeCode,plantPlaceCode);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        PostResponseDto postResponseDto = null;

        for(Post post : postList){
            postResponseDto = new PostResponseDto(post.getPostId(),post.getPostTitle(),post.getPostImgUrl());
            postResponseDtoList.add(postResponseDto);
        }
        return postResponseDtoList;
    }

    // 게시글 상세조회
    public void readPostDetail(Long postId) {

    }

    // 게시글 작성하기
    public Post writePost(PostWriteRequestDto post, User user) {
         PlantPlace plantPlace = commUtils.getPlantPlace(post.getPlantPlaceCode());
         PostType postType = commUtils.getPostType(post.getPostTypeCode());
        Post writePost = new Post(post,user,plantPlace,postType);

        return postRepository.save(writePost);
    }
    // 게시글 삭제
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
