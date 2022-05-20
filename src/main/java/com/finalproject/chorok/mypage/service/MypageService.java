package com.finalproject.chorok.mypage.service;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.mypage.dto.MyPlanteriorSearchResponseDto;
import com.finalproject.chorok.mypage.model.PlantBookMark;
import com.finalproject.chorok.mypage.repository.PlantBookMarkRepository;
import com.finalproject.chorok.post.dto.CommunityResponseDto;
import com.finalproject.chorok.post.dto.PlantariaDictionaryResponseDto;
import com.finalproject.chorok.post.dto.PlantriaFilterRequestDto;
import com.finalproject.chorok.post.repository.PostRepository;
import com.finalproject.chorok.post.utils.CommUtils;
import com.finalproject.chorok.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final PlantBookMarkRepository plantBookMarkRepository;
    private final CommUtils commUtils;
    private final PostRepository postRepository;

    // 식물 북마크
    @Transactional
    public HashMap<String, String> plantBookMark(Long plantNo, User user) {
        if(commUtils.getPlantBookMark(user,plantNo)!=null){
            // 북마크 삭제
            plantBookMarkRepository.deleteByUserPlantBookMarkQuery(user.getUserId(),plantNo);
            return commUtils.toggleResponseHashMap(false);

        }else{
            // 북마크 추가
            plantBookMarkRepository.save(new PlantBookMark(user,commUtils.getPlant(plantNo)));
            return commUtils.toggleResponseHashMap(true);
        }
    }
    // 마이페이지 내가쓴 플렌테이어 6개, 북마크한 플렌테리어 조회 6개

    public MyPlanteriorSearchResponseDto myPlanterior(UserDetailsImpl userDetails) {
        return new MyPlanteriorSearchResponseDto(
                //1. 내가쓴 글 카운트
                postRepository.myPlanteriorCount(userDetails.getUserId()),
                //2. 내가쓴 글 6개
                postRepository.myPlanterior(userDetails.getUserId()),
                //3. 내가 북마크한 글 카운트
                postRepository.myPlanteriorBookMarkCount(userDetails.getUserId()),
                //4. 내가 북마크한 글 6개
                postRepository.myPlanteriorBookMark(userDetails.getUserId())
        );
    }

    // 내가 작성한 플렌테리어-전체조회
    public Page<CommunityResponseDto> myPhoto(UserDetailsImpl userDetails, PlantriaFilterRequestDto plantriaFilterRequestDto, Pageable pageable) {
        return postRepository.myPlanterior(userDetails.getUserId(),plantriaFilterRequestDto,pageable);
    }

    // 내가 북마크한 게시물
    public Page<CommunityResponseDto> myPostBookMark(UserDetailsImpl userDetails,PlantriaFilterRequestDto plantriaFilterRequestDto,Pageable pageable) {
        return postRepository.myBookMarkPost(userDetails.getUserId(),plantriaFilterRequestDto,pageable);
    }

    // 내가 북마크한 식물
    public PlantariaDictionaryResponseDto myPlantBookMark(UserDetailsImpl userDetails) {

        return  new PlantariaDictionaryResponseDto(
                postRepository.myPlantBookMark(userDetails.getUserId()).size()
                ,postRepository.myPlantBookMark(userDetails.getUserId())
        );
    }


}
