package com.finalproject.chorok.post.utils;

import com.finalproject.chorok.common.Image.Image;
import com.finalproject.chorok.common.Image.ImageRepository;
import com.finalproject.chorok.common.Image.S3Uploader;
import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.mypage.model.PlantBookMark;
import com.finalproject.chorok.mypage.repository.PlantBookMarkRepository;
import com.finalproject.chorok.plant.model.Plant;
import com.finalproject.chorok.plant.repository.PlantRepository;
import com.finalproject.chorok.post.dto.PostWriteRequestDto;
import com.finalproject.chorok.post.dto.comment.CommentResponseDto;
import com.finalproject.chorok.post.model.*;
import com.finalproject.chorok.post.repository.*;
import com.finalproject.chorok.plant.model.PlantPlace;
import com.finalproject.chorok.plant.repository.PlantPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommUtils {
    private final PlantRepository plantRepository;
    private final PlantPlaceRepository plantPlaceRepository;
    private final PostTypeRepository postTypeRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final PostBookMarkRepository postBookMarkRepository;
    private final ImageRepository imageRepository;
    private final PlantBookMarkRepository plantBookMarkRepository;
    private final S3Uploader s3Uploader;



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
    // 식물번호로 식물 조회
    public Plant getPlant(Long plantNo){
        return plantRepository.findById(plantNo).orElseThrow(
                () -> new NullPointerException("해당 식물 아이디가 존재하지 않습니다.")
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

    // 식물 북마크 조회
    public PlantBookMark getPlantBookMark(User user, Long plantNo){

        return plantBookMarkRepository.findUserPlantBookMarkQuery(user.getUserId(),plantNo);

    }

    // 반환값 없는 API 반환값 설정
    public HashMap<String,String> responseHashMap(HttpStatus httpCode){
        HashMap<String,String> hs = new HashMap<>();

        hs.put("StatusCode",String.valueOf(httpCode));
        hs.put("msg","성공적으로 완료되었습니다");
        return hs;
    }
    // 좋아요 북마크 반환값
    public HashMap<String,String> toggleResponseHashMap(Boolean result){
        HashMap<String,String> hs = new HashMap<>();

        hs.put("result",String.valueOf(result));
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

    // 게시글에 사진 있는지 확인하고 있으면 삭제
    public void postPhotoDelete(Long postId) {
        Post post = getPost(postId);
        if( post.getPostImgUrl() == null || post.getPostImgUrl().equals("") || post.getPostImgUrl().isEmpty() ){
            imageRepository.deleteByImageUrl(post.getPostImgUrl());
        }
    }
    // 사진 저장
    public String postPhotoSave(MultipartFile file) throws IOException {
        if(file == null ||file.isEmpty() || file.equals("")){
            return null;
        }
        return s3Uploader.upload(file, "static");
    }


    // 플랜테리어 사진 유무 체크
    public void planteriorFileChk(String postTypeCode, MultipartFile file){
        if(postTypeCode.equals("postType01") || postTypeCode == "postType01"){
            if(file == null || file.isEmpty())
                throw new NullPointerException("플랜테이어는 사진이 필수조건입니다.");
        }
    }



    // 게시글 등록할때 플렌테리어 이외를 게시판 plantPlaceCode ExceptionCHk

    public String planteriorPlantPlaceChk(String postTypeCode, String plantPlaceCode) {

        if (postTypeCode.equals("postType01")) {
            if(plantPlaceCode==null || plantPlaceCode.equals("")){
                throw new NullPointerException("플렌테리어는 plantPlaceCode가 필수요소입니다.");
            }
            return plantPlaceCode;
        }
        return null;

    }
    // 사진 업데이트 할때 image 변환유무 체크
    public Post originalUrlChk(Long postId, String originalUrl, PostWriteRequestDto postRequestDto) throws IOException {
        Post post = getPost(postId);
        PostType postType = getPostType(postRequestDto.getPostTypeCode());
        Image image = imageRepository.findByImageUrl(post.getPostImgUrl());
        // 1. 비어있을때
        if(originalUrl == null || originalUrl.equals("")){
            // s3에서 이미지 삭제
            //s3Uploader.deleteImage(image.getFilename());
            // image
            imageRepository.deleteByImageUrl(post.getPostImgUrl());

            // 1-1. 사진 삭제
            if(postRequestDto.getPostImgUrl()==null || postRequestDto.getPostImgUrl().isEmpty()){
                post.updateDeleteImage(postRequestDto,postType);
            }
            // 1-2. 사진 수정
            else{
                String imageUrl = postPhotoSave(postRequestDto.getPostImgUrl());
                post.update(postRequestDto,imageUrl,postType);
            }

        }
        // 2. 들어있을때 유지
        else{
            post.update(postRequestDto,originalUrl,postType);
        }
        return post;
    }


    // 반환값 없는 에러 API 반환값 설정
    public HashMap<String,String> errResponseHashMap(HttpStatus httpCode){

        HashMap<String,String> hs = new HashMap<>();
        hs.put("StatusCode",String.valueOf(httpCode));
        hs.put("msg","작업을 완료하지 못했습니다");
        return hs;
    }

    // 게시글 수정 삭제 권한 체크
    public void postAuthChk(Long userId,Long postId) throws IllegalAccessException {
        Post post = getPost(postId);
        if(post.getUser().getUserId()!=userId){
             throw new IllegalAccessException("권한이 없습니다.");
        }

    }

    // 댓글 수정 삭제 권한 체크
    public void commentAuthChk(Long commentId,Long userId) throws IllegalAccessException {
        Comment comment = getComment(commentId);
        if(comment.getUser().getUserId()!=userId){
            throw new IllegalAccessException("권한이 없습니다.");
        }

    }

}

