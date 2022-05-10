package com.finalproject.chorok.post.utils;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.post.dto.comment.CommentResponseDto;
import com.finalproject.chorok.post.model.*;
import com.finalproject.chorok.post.repository.*;
import com.finalproject.chorok.plant.model.PlantPlace;
import com.finalproject.chorok.plant.repository.PlantPlaceRepository;
import com.finalproject.chorok.security.jwt.JwtDecoder;
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
    private final JwtDecoder jwtDecoder;


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
    public Comment getComment(Long commentId){
        return commentRepository.findById(commentId).orElseThrow(
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

    // 게시글 좋아요 값 유무 확인
    public Boolean LikePostChk(Long postId, User user){
        return getLikePost(postId, user) != null;
    }
    // 게시글 북마크 값 유무 확인
    public Boolean BookMarkPostChk(Long postId, User user){
        return getBookMarkPost(postId, user) != null;
    }

    // 초록톡 - 전체 postTypeCode 조회
    public List<String> getAllCommunityCode(){
        return postTypeRepository.findByAllCommunityQuery();
    }

    // 초록톡 - postTypeCode 하나 조회
    public List<String> getCommunityCode(String postTypeCode){

        return postTypeRepository.findAllByPostTypeCodeQuery(postTypeCode);
    }

    // JWT token 으로 username 뽑기
    public String getUsernameToToken(String token){
        System.out.println(token.split(" ")[1]);
        return jwtDecoder.decodeUsername(token.split(" ")[1]);

    }
}

