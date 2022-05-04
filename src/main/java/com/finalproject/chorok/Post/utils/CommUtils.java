package com.finalproject.chorok.Post.utils;

import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.Post.model.Comment;
import com.finalproject.chorok.Post.model.Post;
import com.finalproject.chorok.Post.model.PostLike;
import com.finalproject.chorok.Post.model.PostType;
import com.finalproject.chorok.Post.repository.CommentRepository;
import com.finalproject.chorok.Post.repository.PostLikeRepository;
import com.finalproject.chorok.Post.repository.PostRepository;
import com.finalproject.chorok.plant.model.PlantPlace;
import com.finalproject.chorok.plant.repository.PlantPlaceRepository;
import com.finalproject.chorok.Post.repository.PostTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommUtils {
    private final PlantPlaceRepository plantPlaceRepository;
    private final PostTypeRepository postTypeRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;


    // 식물장소코드로 식물장소 검색
    public  PlantPlace getPlantPlace(String plantPlaceCode){
       return plantPlaceRepository.findById(plantPlaceCode).orElseThrow(
               () -> new NullPointerException("해당 식물장소 아이디가 존재하지 않습니다.")
       );

    }
    // 게시글타입코드로 게시글타입 검색
    public  PostType getPostType(String postTypeCode){
        return postTypeRepository.findById(postTypeCode).orElseThrow(
                () -> new NullPointerException("해당 게시글 타입이 존재하지 않습니다.")
        );
    }

    // 게시글 번호로 게시글 조회
    public Post getPost(Long postId){
        return postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
    }
    // 게시글 번호로 댓글 리스트 조회
    public List<Comment> getCommentList(Long postId){
        return commentRepository.findByPostPostId(postId);
    }
    // 게시글 좋아요 조회
    public PostLike getLikePost(Long postId, User user){

        System.out.println(postLikeRepository.findUserLikeQuery(user.getUserId(),postId));
        return postLikeRepository.findUserLikeQuery(user.getUserId(),postId);

    }
}
