package com.finalproject.chorok.mypage.service;

import com.finalproject.chorok.common.Image.Image;
import com.finalproject.chorok.common.Image.ImageRepository;
import com.finalproject.chorok.common.Image.S3Uploader;
import com.finalproject.chorok.login.dto.DuplicateChkDto;
import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.mypage.dto.*;
import com.finalproject.chorok.mypage.repository.PlantBookMarkRepository;
import com.finalproject.chorok.post.dto.CommunityResponseDto;
import com.finalproject.chorok.post.dto.PlantDictionaryResponseDto;
import com.finalproject.chorok.post.dto.PlantariaDictionaryResponseDto;
import com.finalproject.chorok.post.dto.PlantriaFilterRequestDto;
import com.finalproject.chorok.login.repository.UserRepository;
import com.finalproject.chorok.login.validator.Validator;
import com.finalproject.chorok.myPlant.dto.MyAllPlantDetailResponseDto;
import com.finalproject.chorok.myPlant.model.MyPlant;
import com.finalproject.chorok.myPlant.repository.MyPlantRepository;
import com.finalproject.chorok.mypage.model.PlantBookMark;
import com.finalproject.chorok.plant.repository.PlantRepository;

import com.finalproject.chorok.post.repository.PostRepository;
import com.finalproject.chorok.post.utils.CommUtils;
import com.finalproject.chorok.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final PlantBookMarkRepository plantBookMarkRepository;
    private final CommUtils commUtils;
    private final PostRepository postRepository;
    private final MyPlantRepository myPlantRepository;
    private final PlantRepository plantRepository;
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;
    private final ImageRepository imageRepository;



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
    public MypagePagingDto myPhoto(UserDetailsImpl userDetails, PlantriaFilterRequestDto plantriaFilterRequestDto, Pageable pageable) {

        return new MypagePagingDto(
                postRepository.myPlanterior(userDetails.getUserId(),plantriaFilterRequestDto,pageable)
        );
    }


    // 내가 북마크한 커뮤니티 전체 조회 ( 커뮤니티 )
    public MypagePagingDto myCommunityBookMark(UserDetailsImpl userDetails, Pageable pageable) {
        return new MypagePagingDto(
                postRepository.myCommunityBookMark(userDetails.getUserId(),pageable)
        );
    }

    // 내가 쓴 커뮤니티 게시물 전체 조회 ( 커뮤니티)
    public MypagePagingDto myCommunity(UserDetailsImpl userDetails, Pageable pageable) {
        return new MypagePagingDto(
                postRepository.myCommunity(userDetails.getUserId(),pageable)
        );
    }

    // 내가 북마크한 게시물
    public MypagePagingDto myPostBookMark(UserDetailsImpl userDetails,PlantriaFilterRequestDto plantriaFilterRequestDto,Pageable pageable) {
        return new MypagePagingDto(
                postRepository.myBookMarkPost(userDetails.getUserId(),plantriaFilterRequestDto,pageable)
        );
    }

    // 내가 북마크한 식물
    public MypagePagingDto myPlantBookMark(UserDetailsImpl userDetails, Pageable pageable) {

        return new MypagePagingDto(
                postRepository.myPlantBookMark(userDetails.getUserId(),pageable)
        );
    }

//    public List<MyAllPlantDetailResponseDto> getAllMyPlantDetail(UserDetailsImpl userDetails) {
//        List<MyPlant> myPlants = myPlantRepository.findAllByUser(userDetails.getUser());
//        List<MyAllPlantDetailResponseDto> myAllPlantDetailResponseDtos = new ArrayList<>();
//        for (MyPlant myPlant : myPlants) {
//            MyAllPlantDetailResponseDto myAllPlantDetailResponseDto = new MyAllPlantDetailResponseDto(
//                    myPlant.getMyPlantNo(),
//                    myPlant.getMyPlantImgUrl(),
//                    myPlant.getMyPlantPlace(),
//                    myPlant.getMyPlantName(),
//                    plantRepository.findByPlantNo(myPlant.getPlantNo()).getPlantName(),
//                    myPlant.getStartDay(),
//                    myPlant.getEndDay()
//            );
//            myAllPlantDetailResponseDtos.add(myAllPlantDetailResponseDto);
//        }
//
//
//        return myAllPlantDetailResponseDtos;
//    }

    //비밀번호 수정
    public HashMap<String, String> updatePassword(ProfileUpdateDto profileUpdateDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        String password = profileUpdateDto.getPassword();
        validator.passwordCheck(password);
        String encodedPassword = passwordEncoder.encode(password);
        user.changePassword(encodedPassword);
        userRepository.save(user);
        return commUtils.responseHashMap(HttpStatus.OK);
    }

    //프로필 닉네임, 사진 수정
    @Transactional
    public HashMap<String, String> updateProfile(String nickname, MultipartFile multipartFile, String profileMsg, String originalUrl, UserDetailsImpl userDetails) throws IOException {
        System.out.println("서비스 들어오나");
        if (!nickname.equals(userDetails.getNickname())) {
            validator.nickCheck(new DuplicateChkDto(nickname));}
        User user = userDetails.getUser();
        Image image = imageRepository.findByImageUrl(user.getProfileImageUrl());

        try {
            //originalUrl이 널값일때->멀티파트파일이 있을때
//            if (originalUrl == null || originalUrl.equals(""))
            if (!multipartFile.isEmpty()&&image!=null) {
                //사진삭제
                s3Uploader.deleteImage(image.getFilename());
                imageRepository.deleteByImageUrl(user.getProfileImageUrl());
                String updatedProfileImgUrl = s3Uploader.upload(multipartFile, "static");
                user.changeProfileImage(updatedProfileImgUrl);
                user.changeNickname(nickname);
                user.changeProfileMsg(profileMsg);
                userRepository.save(user);
            }
            if(!multipartFile.isEmpty()&&image==null){
                String updatedProfileImgUrl = s3Uploader.upload(multipartFile, "static");
                user.changeProfileImage(updatedProfileImgUrl);
                user.changeNickname(nickname);
                user.changeProfileMsg(profileMsg);
                userRepository.save(user);
            }
            return commUtils.responseHashMap(HttpStatus.OK);

        }
        catch (NullPointerException e) {
            //멀티파트가 null일때니까, originalImgurl로 간다.
            user.changeProfileImage(originalUrl);
            user.changeNickname(nickname);
            user.changeProfileMsg(profileMsg);
            userRepository.save(user);
            return commUtils.responseHashMap(HttpStatus.OK);
        }
        catch (IOException e) {
            e.printStackTrace();
            return commUtils.responseHashMap(HttpStatus.OK);

        }
    }

//내식물 6개보기
    public MypageMyplantFinalDto getSixMyplants(UserDetailsImpl userDetails){
        List<MyPlant> myPlants = myPlantRepository.findTop6ByUserOrderByMyPlantNameAsc(userDetails.getUser());
        List<MypageMyplantSixDto> mypageMyplantSixDtos = new ArrayList<>();
        for (MyPlant myPlant :myPlants){
            MypageMyplantSixDto mypageMyplantSixDto = new MypageMyplantSixDto(
                    myPlant.getMyPlantImgUrl(),
                    myPlant.getMyPlantName(),
                    plantRepository.findByPlantNo(myPlant.getPlantNo()).getPlantName()
            );
            mypageMyplantSixDtos.add(mypageMyplantSixDto);
        }
        MypageMyplantFinalDto mypageMyplantFinalDto = new MypageMyplantFinalDto(
                myPlantRepository.findAllByUser(userDetails.getUser()).size(),
                mypageMyplantSixDtos);
        return mypageMyplantFinalDto;
    }
    // 내가 스크랩한 식물 6개
    public MypageMyBookMarkplantFinalDto getSixBookmarkPlant(UserDetailsImpl userDetails, Pageable pageable) {
        return new MypageMyBookMarkplantFinalDto(
                postRepository.myPlantBookMark(userDetails.getUserId(),pageable)
        );

    }

    //계정 비활성화
    public HashMap<String, String> inactivateAccount(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        user.changeAccountStatus(false);
        user.changePassword(UUID.randomUUID().toString());
        user.changeUsername(UUID.randomUUID().toString());
        userRepository.save(user);
        return commUtils.responseHashMap(HttpStatus.OK);
    }



}
