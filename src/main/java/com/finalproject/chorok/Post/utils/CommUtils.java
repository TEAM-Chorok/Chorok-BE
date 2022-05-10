package com.finalproject.chorok.Post.utils;

import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.Post.dto.CommentResponseDto;
import com.finalproject.chorok.Post.model.*;
import com.finalproject.chorok.Post.repository.*;
import com.finalproject.chorok.plant.model.PlantPlace;
import com.finalproject.chorok.plant.repository.PlantPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommUtils {
    private final PlantPlaceRepository plantPlaceRepository;
    private final PostTypeRepository postTypeRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final PostBookMarkRepository postBookMarkRepository;


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
    // 댓글 번호로 댓글 조회
    public Comment getComment(Long commentNo){
        return commentRepository.findById(commentNo).orElseThrow(
                ()-> new NullPointerException("해당 댓글이 존재하지 않습니다.")
        );
    }
    // 게시글 번호로 댓글 리스트 조회
    public List<CommentResponseDto>  getCommentList(Long postId){
        return commentRepository.findCommentToPostIdQuery(postId);
    }
    // 게시글 좋아요 조회
    public PostLike getLikePost(Long postId, User user){

        return postLikeRepository.findUserLikeQuery(user.getUserId(),postId);

    }
    // 게시글 북마크 조회
    public PostBookMark getBookMarkPost(Long postId, User user){

        return postBookMarkRepository.findUserBookMarkQuery(user.getUserId(),postId);

    }
    // 반환값 없는 API 반환값 설정
    public HashMap<String,String> responseHashMap(HttpStatus httpCode){
        HashMap<String,String> hs = new HashMap<>();
        hs.put("StatusCode",String.valueOf(httpCode));
        hs.put("msg","성공적으로 완료되었습니다");
        return hs;
    }
}
