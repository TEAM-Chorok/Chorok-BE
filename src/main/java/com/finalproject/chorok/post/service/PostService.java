package com.finalproject.chorok.post.service;

import com.finalproject.chorok.common.Image.S3Uploader;
import com.finalproject.chorok.common.utils.PlantUtils;
import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.plant.model.PlantImg;
import com.finalproject.chorok.plant.repository.PlantImgRepository;
import com.finalproject.chorok.plant.repository.PlantRepository;
import com.finalproject.chorok.post.dto.*;
import com.finalproject.chorok.post.dto.comment.CommentResponseDto;
import com.finalproject.chorok.post.model.*;
import com.finalproject.chorok.post.repository.CommentRepository;
import com.finalproject.chorok.post.repository.PostBookMarkRepository;
import com.finalproject.chorok.post.repository.PostLikeRepository;
import com.finalproject.chorok.post.repository.PostRepository;
import com.finalproject.chorok.post.utils.CommUtils;
import com.finalproject.chorok.plant.model.PlantPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    private final PostBookMarkRepository postBookMarkRepository;
    private final CommUtils commUtils;

    // 1.플랜테리어 조회(queryParameter로 필터링)
    public PostPagingDto planteriorReadPosts(PlantriaFilterRequestDto postSearchRequestDto, Pageable pageable) {

      return new PostPagingDto(
              postRepository.planteriorReadPosts(postSearchRequestDto,pageable)
      );
    }
    // 2. 초록톡 전체 게시물조회 (postTypeCode로 필터링) - 로그인 시
    public PostPagingDto readPostsCommunity(User user,String postTypeCode,Pageable pageable) {

        //List<CommunityResponseDto> communityResponseDtoList = postRepository.chorokTalkList(user.getUserId(),postTypeCode);

       // return communityResponseDtoList;
        return new PostPagingDto(
                postRepository.chorokTalkList(user.getUserId(),postTypeCode,pageable)
        );
    }


    // 3. 초록톡 전체 게시물조회 (postTypeCode로 필터링) - 비로그인 시
    public PostPagingDto nonLoginReadPostsCommunity(String postTypeCode,Pageable pageable) {
//        List<CommunityResponseDto> communityResponseDtoList = postRepository.non_login_chorokTalkList(postTypeCode);
//        return communityResponseDtoList;
        return new PostPagingDto(
                postRepository.non_login_chorokTalkList(postTypeCode,pageable)
        );
    }




    // 4. 게시글 상세조회
    @Transactional
    public PostDetailResponseDto readPostDetail(Long postId,User user) {
        //게시글 조회
        Post post = commUtils.getPost(postId);
        // 댓글리스트 Dto
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        // 댓글리스트
        for(Comment comment : post.getCommentList()){
            commentResponseDtos.add(new CommentResponseDto(comment));
        }
        // 녹색톡 상세조회
        if(post.getPlantPlaceCode()== null || post.getPlantPlaceCode().equals("")){
            return new PostDetailResponseDto(
                    post,
                    commUtils.LikePostChk(postId,user),
                    commUtils.BookMarkPostChk(postId,user),
                    commentResponseDtos
            );
        }
        // 식물 장소
        PlantPlace plantPlace = commUtils.getPlantPlace(post.getPlantPlaceCode());
        // 플렌테이어 상세조회
        return new PostDetailResponseDto(
                post,
                plantPlace,
                commUtils.LikePostChk(postId,user),
                commUtils.BookMarkPostChk(postId,user),
                commentResponseDtos,
                user.getProfileMsg()
        );
    }

    // 5. 게시글 작성하기
    @Transactional
    public PostResponseDto writePost(PostWriteRequestDto post, User user) throws IOException {

        // 1. 플랜테리어 사진 유무 체크
        commUtils.planteriorFileChk(post.getPostTypeCode(),post.getPostImgUrl());
        // 2. 플랜테리어 와 초록톡 plantPlaceCode 체크
        post.setPlantPlaceCode(commUtils.planteriorPlantPlaceChk(post.getPostTypeCode(),post.getPlantPlaceCode()));
        // 3. postTypeCode 유효성 체크
        PostType postType = commUtils.getPostType(post.getPostTypeCode());
        // 사진 저장
        String postImgUrl = commUtils.postPhotoSave(post.getPostImgUrl());
        Post writePost =  postRepository.save(new Post(post,user,postType,postImgUrl));

        return new PostResponseDto(writePost);
    }

    // 6. 게시글 삭제
    @Transactional
    public HashMap<String, String> deletePost(Long postId,User user) throws IllegalAccessException {
        commUtils.postAuthChk(user.getUserId(), postId);
        // 게시글에 사진 있는지 확인하고 있으면 삭제
        commUtils.postPhotoDelete(postId);
        // 게시글 삭제
        postRepository.deleteById(postId);
        return commUtils.responseHashMap(HttpStatus.OK);

    }

    // 7. 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long postId, PostWriteRequestDto postRequestDto,String originalUrl,User user) throws IOException, IllegalAccessException {
        commUtils.postAuthChk(user.getUserId(),postId);
        if(originalUrl == null || originalUrl.equals("")){
            // 1. 플랜테리어 사진 유무 체크
            commUtils.planteriorFileChk(postRequestDto.getPostTypeCode(),postRequestDto.getPostImgUrl());
        }
        // 2. 플랜테리어 와 초록톡 plantPlaceCode 체크
        postRequestDto.setPlantPlaceCode(commUtils.planteriorPlantPlaceChk(postRequestDto.getPostTypeCode(),postRequestDto.getPlantPlaceCode()));
        // 3. postTypeCode 유효성 체크
        PostType postType = commUtils.getPostType(postRequestDto.getPostTypeCode());

        return new PostResponseDto(commUtils.originalUrlChk(postId,originalUrl,postRequestDto));
    }

    // 8. 게시글 좋아요
    @Transactional
    public HashMap<String, String> likePost(Long postId, User user) {

        if(commUtils.getLikePost(postId,user)!=null){
            // 좋아요 삭제
            postLikeRepository.deleteByUser_UserIdAndPost_PostId(user.getUserId(),postId);
            int count =postLikeRepository.findByPostPostIdAndUserUserId(postId,user.getUserId()).size();
            return commUtils.toggleResponseHashMap(false,count,postId);
        }else{
            // 좋아요 추가
            postLikeRepository.save(new PostLike(commUtils.getPost(postId),user));
            int count =postLikeRepository.findByPostPostIdAndUserUserId(postId,user.getUserId()).size();
            return commUtils.toggleResponseHashMap(true,count,postId);
        }

    }
    // 9. 게시글 북마크
    @Transactional
    public HashMap<String, String> bookMarkPost(Long postId, User user) {

        if(commUtils.getBookMarkPost(postId,user)!=null){
            // 북마크 삭제
            postBookMarkRepository.deleteByUserBookMarkQuery(user.getUserId(),postId);

            return commUtils.toggleResponseHashMap(false);

        }else{
            // 북마크 추가
            postBookMarkRepository.save(new PostBookMark(commUtils.getPost(postId),user));
            return commUtils.toggleResponseHashMap(true);
        }

    }

    // 10. 플랜테리어 통합검색
    @Transactional
    public PostSearchResponseDto integrateSearchPlanterior(PlantriaFilterRequestDto postSearchRequestDto) {
        // 플랜테이어 검색 - 갯수
        Long planteriorCount =  postRepository.integrateSearchPlanteriorCount(postSearchRequestDto);
        // 플랜테이어 검색(제목,내용)
        List<PlantriaSearchResponseDto> planteriorSearchList = postRepository.integrateSearchPlanterior(postSearchRequestDto);
        // 식물도감 검색 - 갯수
        Long plantDictionaryCount = postRepository.plantDictionaryListCount(postSearchRequestDto);
        // 식물도감 검색 (이름)
       // List<PlantImg> plantDictionaryList =postRepository.integratePlantDictionaryList(postSearchRequestDto);
        List<PlantDictionaryResponseDto> planteriorDictionaryList = postRepository.planteriorDictionaryList(postSearchRequestDto);

        PostSearchResponseDto postSearchResponseDto = new PostSearchResponseDto(
                planteriorCount,
                planteriorSearchList,
                plantDictionaryCount,
               // getPlantDictionarySearchList(plantDictionaryList)
                planteriorDictionaryList
        );

        return postSearchResponseDto;
    }


    // 11. 플랜테이어 통합 검색 결과 -  사진
    public PostPagingDto photoSearchPlanterior(PlantriaFilterRequestDto postSearchRequestDto, Pageable pageable) {
       // Page<PostResponseDto> responseDtoList = postRepository.planteriorReadPosts(postSearchRequestDto, pageable);
       // int planteriorCount  = Math.toIntExact(postRepository.integrateSearchPlanteriorCount(postSearchRequestDto));


        //return new PlantriaPhotoResponseDto(planteriorCount,responseDtoList);
        return new PostPagingDto(
            postRepository.planteriorReadPosts(postSearchRequestDto, pageable)
        );
    }

    // 식물 도감 검색
    public PostPagingDto dictionarySearchPlantria(DictionaryFilterDto dictionaryFilterDto,Pageable pageable) {

        // 식물도감검색
        //List<PlantDictionaryResponseDto> plantDictionaryList = postRepository.plantDictionaryList(dictionaryFilterDto,pageable);

       // return new PlantariaDictionaryResponseDto(plantDictionaryList.size(),plantDictionaryList);
        return  new PostPagingDto(
                postRepository.plantDictionaryList(dictionaryFilterDto,pageable)
        );
    }


}
