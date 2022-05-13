package com.finalproject.chorok.post.service;

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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
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
    private final CommUtils commUtils;
    private final CommentRepository commentRepository;
    private final PostBookMarkRepository postBookMarkRepository;
    private final PlantRepository plantRepository;
    private final PlantImgRepository plantImgRepository;
    private final PlantUtils plantUtils;


    /*//1. 플랜테리어 전체 게시물 조회 - 로그인, 비로그인 상관없을거 같음
    @Transactional
    public List<PostResponseDto> readPosts(PlantriaFilterRequestDto postSearchRequestDto) {

        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        PostResponseDto postResponseDto;

        for(Post post : readPostsQuery(postSearchRequestDto)){
            postResponseDto = new PostResponseDto(post);
            postResponseDtoList.add(postResponseDto);
        }
        return postResponseDtoList;
    }

    // 1-1. 플랜테이어 전체 게시물 조회 - 쿼리
    public List<Post> readPostsQuery(PlantriaFilterRequestDto postSearchRequestDto){
        List<Post> postList;

        if(postSearchRequestDto.getPlantPlaceCode()==null || postSearchRequestDto.getPlantPlaceCode().equals("")){
            postList = postRepository.findAllByPostTypePostTypeCodeOrderByCreatedAtDesc(postSearchRequestDto.getPostTypeCode());
        }else{
            postList = postRepository.findAllByPostTypePostTypeCodeAndPlantPlaceCodeOrderByCreatedAtDesc(postSearchRequestDto.getPostTypeCode(),postSearchRequestDto.getPlantPlaceCode());
        }
        return postList;
    }*/

    // 2. 초록톡 전체 게시물조회 (postTypeCode로 필터링) - 로그인 시
    public List<CommunityResponseDto> readPostsCommunity(User user,String postTypeCode) {

        List<CommunityResponseDto> communityResponseDtoList = postRepository.chorokTalkList(user.getUserId(),postTypeCode);

//        for(Post communityPost : readPostsCommunityQuery(postTypeCode)){
//            CommunityResponseDto communityResponseDto = new CommunityResponseDto(
//                    communityPost,
//                    commUtils.LikePostChk(communityPost.getPostId(),user),
//                    commUtils.BookMarkPostChk(communityPost.getPostId(),user)
//            );
//            communityResponseDtoList.add(communityResponseDto);
//        }
        return communityResponseDtoList;
    }


    // 3. 초록톡 전체 게시물조회 (postTypeCode로 필터링) - 비로그인 시
    public List<CommunityResponseDto> nonLoginReadPostsCommunity(String postTypeCode) {
        List<CommunityResponseDto> communityResponseDtoList = postRepository.non_login_chorokTalkList(postTypeCode);

//        for(Post communityPost : readPostsCommunityQuery(postTypeCode)){
//            CommunityResponseDto communityResponseDto = new CommunityResponseDto(
////                    communityPost
//            );
//            communityResponseDtoList.add(communityResponseDto);
//        }
        return communityResponseDtoList;
    }

//    // 2-2,3-2. 초록톡 전체 게시물 조회 - 쿼리
//    public List<Post> readPostsCommunityQuery(String postTypeCode){
//
//        List<Post> communityList = new ArrayList<>();
//        if(postTypeCode == null || postTypeCode.equals("")){
//            communityList =postRepository.findByPostTypePostTypeCodeInOrderByCreatedAt(commUtils.getAllCommunityCode());
//        }else{
//            communityList =postRepository.findByPostTypePostTypeCodeInOrderByCreatedAt(commUtils.getCommunityCode(postTypeCode));
//        }
//        return communityList;
//    }


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
                    commentResponseDtos);
        }
        // 식물 장소
        PlantPlace plantPlace = commUtils.getPlantPlace(post.getPlantPlaceCode());

        // 플렌테이어 상세조회
        return new PostDetailResponseDto(
                post,
                plantPlace,
                commUtils.LikePostChk(postId,user),
                commUtils.BookMarkPostChk(postId,user),
                commentResponseDtos);
    }

    // 5. 게시글 작성하기
    @Transactional
    public HashMap<String, String> writePost(PostWriteRequestDto post, User user) {

        PostType postType = commUtils.getPostType(post.getPostTypeCode());
        Post writePost = new Post(post,user,postType);
        postRepository.save(writePost);
        return commUtils.responseHashMap(HttpStatus.OK);
    }

    // 6. 게시글 삭제
    @Transactional
    public HashMap<String, String> deletePost(Long postId) {
        postRepository.deleteById(postId);
        return commUtils.responseHashMap(HttpStatus.OK);

    }

    // 7. 게시글 수정
    @Transactional
    public HashMap<String, String> updatePost(Long postId, PostRequestDto postRequestDto) {
        Post post =  commUtils.getPost(postId);
        post.update(postRequestDto);
        return commUtils.responseHashMap(HttpStatus.OK);

    }

    // 8. 게시글 좋아요
    @Transactional
    public HashMap<String, String> likePost(Long postId, User user) {

        if(commUtils.getLikePost(postId,user)!=null){
            // 좋아요 삭제
            postLikeRepository.deleteByUser_UserIdAndPost_PostId(user.getUserId(),postId);
            return commUtils.toggleResponseHashMap(false);
        }else{
            // 좋아요 추가
            postLikeRepository.save(new PostLike(commUtils.getPost(postId),user));
            return commUtils.toggleResponseHashMap(true);
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
        List<PlantImg> plantDictionaryList =postRepository.integratePlantDictionaryList(postSearchRequestDto);

        PostSearchResponseDto postSearchResponseDto = new PostSearchResponseDto(
                planteriorCount,
                planteriorSearchList,
                plantDictionaryCount,
                getPlantDictionarySearchList(plantDictionaryList)
        );

        return postSearchResponseDto;
    }
/*    // 10-1. 플랜테이어 검색 결과 DTO
    public List<PlantriaSearchResponseDto> getPlantriaSearchList(List<Post> plantriaSearchList){

        List<PlantriaSearchResponseDto> responseDtoList = new ArrayList<>();
        PlantriaSearchResponseDto responseDto;

        for(Post post : plantriaSearchList){
            responseDto= new  PlantriaSearchResponseDto(post.getPostId(),post.getPostImgUrl());
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }*/

    // 10-2. 식물도감 검색 결과 DTO
    public List<PlantDictionaryResponseDto> getPlantDictionarySearchList( List<PlantImg> plantDictionaryList){
        List<PlantDictionaryResponseDto> responseDtoList = new ArrayList<>();
        PlantDictionaryResponseDto responseDto;

        for(PlantImg plantImg : plantDictionaryList){
            responseDto= new  PlantDictionaryResponseDto(
                    plantImg.getPlantNo(),
                    plantImg.getPlantName(),
                    plantUtils.getPlantThumbImg(plantImg.getPlantNo()));
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    // 11. 플랜테이어 통합 검색 결과 -  사진
    public PlantriaPhotoResponseDto photoSearchPlanterior(PlantriaFilterRequestDto postSearchRequestDto) {
        List<PostResponseDto> responseDtoList = postRepository.plantriaReadPosts(postSearchRequestDto);
        int planteriorCount  = Math.toIntExact(postRepository.integrateSearchPlanteriorCount(postSearchRequestDto));


/*        if(plantPlaceCode == null || plantPlaceCode.equals("")){
            // 플랜테이어 검색 - 갯수
            plantriaCount =  postRepository.plantariaSearchCountQuery(keyword);
            // 플랜테이어 검색 - 리스트
            List<Post> postList =postRepository.plantariaSearchPhotoQuery(keyword);
            PostResponseDto responseDto;
            for(Post post : postList){
                responseDto = new PostResponseDto(post);
                responseDtoList.add(responseDto);
            }
        }else{
            // 플랜테이어 검색 - 갯수
            plantriaCount =  postRepository.plantariaSearchPhotoToPostTypeCodeCountQuery(keyword,plantPlaceCode);
            // 플랜테이어 검색 - 리스트
            List<Post> postList =postRepository.plantariaSearchPhotoToPostTypeCodeQuery(keyword,plantPlaceCode);
            PostResponseDto responseDto;
            for(Post post : postList){
                responseDto = new PostResponseDto(post);
                responseDtoList.add(responseDto);
            }
        }*/
        return new PlantriaPhotoResponseDto(planteriorCount,responseDtoList);
    }

    // 식물 도감 검색
    public PlantariaDictionaryResponseDto dictionarySearchPlantria(DictionaryFilterDto dictionaryFilterDto) {

        List<PlantDictionaryResponseDto> responseDtoList = new ArrayList<>();
        // 식물도감검색
        List<PlantDictionaryResponseDto> plantDictionaryList = postRepository.plantDictionaryList(dictionaryFilterDto);
        PlantDictionaryResponseDto responseDto;

//        for(PlantImg plantImg : plantDictionaryList){
//            responseDto= new  PlantDictionaryResponseDto(
//                    plantImg.getPlantNo(),
//                    plantImg.getPlantName(),
//                    plantUtils.getPlantThumbImg(plantImg.getPlantNo()));
//            responseDtoList.add(responseDto);
//        }
        return new PlantariaDictionaryResponseDto(plantDictionaryList.size(),responseDtoList);
    }
}
