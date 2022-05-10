package com.finalproject.chorok.post.service;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.post.dto.*;
import com.finalproject.chorok.post.model.Post;
import com.finalproject.chorok.post.model.PostBookMark;
import com.finalproject.chorok.post.model.PostLike;
import com.finalproject.chorok.post.model.PostType;
import com.finalproject.chorok.post.repository.CommentRepository;
import com.finalproject.chorok.post.repository.PostBookMarkRepository;
import com.finalproject.chorok.post.repository.PostLikeRepository;
import com.finalproject.chorok.post.repository.PostRepository;
import com.finalproject.chorok.post.utils.CommUtils;
import com.finalproject.chorok.plant.model.PlantPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
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
    private final PostLikeRepository postLikeRepository;
    private final CommUtils commUtils;
    private final CommentRepository commentRepository;
    private final PostBookMarkRepository postBookMarkRepository;



    // 전체 게시물 조회
    @Transactional
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
    @Transactional
    public List<PostResponseDto> readPlantPlacePosts(String postTypeCode, String plantPlaceCode) {
        List<Post> postList = postRepository.findAllByPostTypePostTypeCodeAndPlantPlaceCodeOrderByCreatedAt(postTypeCode,plantPlaceCode);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        PostResponseDto postResponseDto = null;

        for(Post post : postList){
            postResponseDto = new PostResponseDto(post.getPostId(),post.getPostTitle(),post.getPostImgUrl());
            postResponseDtoList.add(postResponseDto);
        }
        return postResponseDtoList;
    }

    // 게시글 상세조회
    @Transactional
    public PostDetailResponseDto readPostDetail(Long postId,User user) {
        //게시글 조회
        Post post = commUtils.getPost(postId);
        // 식물 장소
        PlantPlace plantPlace = commUtils.getPlantPlace(post.getPlantPlaceCode());
        // 댓글리스트
      //  List<CommentResponseDto> commentResponseDtos = new CommentResponseDto(post.getCommentList());
        // responseDto
        PostDetailResponseDto postDetailResponseDto =new PostDetailResponseDto(post,plantPlace);
        // 댓글 조회
        //List<Map<String,Object>> commentFormat =

//        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
//        for(Map<String,Object> comment : commentFormat){
//            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
//            commentResponseDtoList.add(commentResponseDto);
//        }
       return postDetailResponseDto;
    }

    // 게시글 작성하기
    @Transactional
    public Post writePost(PostWriteRequestDto post, User user) {

        PostType postType = commUtils.getPostType(post.getPostTypeCode());
        Post writePost = new Post(post,user,postType);
        return postRepository.save(writePost);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);

    }

    // 게시글 수정
    @Transactional
    public void updatePost(Long postId, PostRequestDto postRequestDto) {
       Post post =  commUtils.getPost(postId);
       post.update(postRequestDto);

    }

    // 게시글 좋아요
    @Transactional
    public Boolean likePost(Long postId,User user) {

       if(commUtils.getLikePost(postId,user)!=null){
            System.out.println("삭제");
            postLikeRepository.deleteByUser_UserIdAndPost_PostId(user.getUserId(),postId);
            return false;

       }else{
            System.out.println("추가");
            postLikeRepository.save(new PostLike(commUtils.getPost(postId),user));
            return true;
       }

    }
    public Object bookMarkPost(Long postId, User user) {

        if(commUtils.getBookMarkPost(postId,user)!=null){

            postBookMarkRepository.deleteByUserBookMarkQuery(user.getUserId(),postId);
            return false;

        }else{

            postBookMarkRepository.save(new PostBookMark(commUtils.getPost(postId),user));
            return true;
        }

    }
}
