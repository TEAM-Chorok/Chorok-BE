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



    // ?????? ?????????
    @Transactional
    public HashMap<String, String> plantBookMark(Long plantNo, User user) {
        if(commUtils.getPlantBookMark(user,plantNo)!=null){
            // ????????? ??????
            plantBookMarkRepository.deleteByUserPlantBookMarkQuery(user.getUserId(),plantNo);
            return commUtils.toggleResponseHashMap(false);

        }else{
            // ????????? ??????
            plantBookMarkRepository.save(new PlantBookMark(user,commUtils.getPlant(plantNo)));
            return commUtils.toggleResponseHashMap(true);
        }
    }
    // ??????????????? ????????? ??????????????? 6???, ???????????? ??????????????? ?????? 6???

    public MyPlanteriorSearchResponseDto myPlanterior(UserDetailsImpl userDetails) {
        return new MyPlanteriorSearchResponseDto(
                //1. ????????? ??? ?????????
                postRepository.myPlanteriorCount(userDetails.getUserId()),
                //2. ????????? ??? 6???
                postRepository.myPlanterior(userDetails.getUserId()),
                //3. ?????? ???????????? ??? ?????????
                postRepository.myPlanteriorBookMarkCount(userDetails.getUserId()),
                //4. ?????? ???????????? ??? 6???
                postRepository.myPlanteriorBookMark(userDetails.getUserId())
        );
    }

    // ?????? ????????? ???????????????-????????????
    public MypagePagingDto myPhoto(UserDetailsImpl userDetails, PlantriaFilterRequestDto plantriaFilterRequestDto, Pageable pageable) {

        return new MypagePagingDto(
                postRepository.myPlanterior(userDetails.getUserId(),plantriaFilterRequestDto,pageable),
                userDetails.getUser().getProfileMsg()

        );
    }


    // ?????? ???????????? ???????????? ?????? ?????? ( ???????????? )
    public MypagePagingDto myCommunityBookMark(UserDetailsImpl userDetails, Pageable pageable) {
        return new MypagePagingDto(
                postRepository.myCommunityBookMark(userDetails.getUserId(),pageable),
                userDetails.getUser().getProfileMsg()

        );
    }

    // ?????? ??? ???????????? ????????? ?????? ?????? ( ????????????)
    public MypagePagingDto myCommunity(UserDetailsImpl userDetails, Pageable pageable) {
        return new MypagePagingDto(
                postRepository.myCommunity(userDetails.getUserId(),pageable),
                userDetails.getUser().getProfileMsg()
        );
    }

    // ?????? ???????????? ?????????
    public MypagePagingDto myPostBookMark(UserDetailsImpl userDetails,PlantriaFilterRequestDto plantriaFilterRequestDto,Pageable pageable) {
        return new MypagePagingDto(
                postRepository.myBookMarkPost(userDetails.getUserId(),plantriaFilterRequestDto,pageable),
                userDetails.getUser().getProfileMsg()

        );
    }

    // ?????? ???????????? ??????
    public MypagePagingDto myPlantBookMark(UserDetailsImpl userDetails, Pageable pageable) {

        return new MypagePagingDto(
                postRepository.myPlantBookMark(userDetails.getUserId(),pageable),
                userDetails.getUser().getProfileMsg()

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

    //???????????? ??????
    public HashMap<String, String> updatePassword(ProfileUpdateDto profileUpdateDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        String password = profileUpdateDto.getPassword();
        validator.passwordCheck(password);
        String encodedPassword = passwordEncoder.encode(password);
        user.changePassword(encodedPassword);
        userRepository.save(user);
        return commUtils.responseHashMap(HttpStatus.OK);
    }

    //????????? ?????????, ?????? ??????
    @Transactional
    public HashMap<String, String> updateProfile(String nickname, MultipartFile multipartFile, String profileMsg, String originalUrl, UserDetailsImpl userDetails) throws IOException {
        System.out.println("????????? ????????????");
        if (!nickname.equals(userDetails.getNickname())) {
            validator.nickCheck(new DuplicateChkDto(nickname));}
        User user = userDetails.getUser();
        Image image = imageRepository.findByImageUrl(user.getProfileImageUrl());

        try {
            //originalUrl??? ????????????->????????????????????? ?????????
//            if (originalUrl == null || originalUrl.equals(""))
            if (!multipartFile.isEmpty()&&image!=null) {
                //????????????
                s3Uploader.deleteImage(image.getFilename());
                imageRepository.deleteByImageUrl(user.getProfileImageUrl());
                String updatedProfileImgUrl = s3Uploader.upload(multipartFile, "static");
                user.changeProfileImage(updatedProfileImgUrl);
                user.changeNickname(nickname);
                user.changeProfileMsg(profileMsg);
                userRepository.save(user);
                System.out.println("????????????1");
            }
            if(!multipartFile.isEmpty()&&image==null){
                String updatedProfileImgUrl = s3Uploader.upload(multipartFile, "static");
                user.changeProfileImage(updatedProfileImgUrl);
                user.changeNickname(nickname);
                user.changeProfileMsg(profileMsg);
                userRepository.save(user);
                System.out.println("????????????2");
            }
            return commUtils.responseHashMap(HttpStatus.OK);

        }
        catch (NullPointerException e) {
            //??????????????? null????????????, originalImgurl??? ??????.
            if(originalUrl.equals("null")||originalUrl.equals("")){
            user.changeProfileImage(null);
            }else {
            user.changeProfileImage(originalUrl);
            }
            user.changeNickname(nickname);
            if(profileMsg.equals("null")||profileMsg.equals("")){
                user.changeProfileMsg(null);
            }else {
                user.changeProfileMsg(profileMsg);
            }
            userRepository.save(user);
            System.out.println("????????????3");
            return commUtils.responseHashMap(HttpStatus.OK);
        }
        catch (IOException e) {
            e.printStackTrace();
            return commUtils.responseHashMap(HttpStatus.OK);

        }
    }

//????????? 6?????????
    public MypageMyplantFinalDto getSixMyplants(UserDetailsImpl userDetails){
        List<MyPlant> myPlants = myPlantRepository.findTop6ByUserOrderByMyPlantNameAsc(userDetails.getUser());
        List<MypageMyplantSixDto> mypageMyplantSixDtos = new ArrayList<>();
        for (MyPlant myPlant :myPlants){
            MypageMyplantSixDto mypageMyplantSixDto = new MypageMyplantSixDto(
                    myPlant.getMyPlantImgUrl(),
                    myPlant.getMyPlantName(),
                    plantRepository.findByPlantNo(myPlant.getPlantNo()).getPlantName(),
                    myPlant.getMyPlantNo()
            );
            mypageMyplantSixDtos.add(mypageMyplantSixDto);
        }
        MypageMyplantFinalDto mypageMyplantFinalDto = new MypageMyplantFinalDto(
                myPlantRepository.findAllByUser(userDetails.getUser()).size(),
                mypageMyplantSixDtos);
        return mypageMyplantFinalDto;
    }
    // ?????? ???????????? ?????? 6???
    public MypageMyBookMarkplantFinalDto getSixBookmarkPlant(UserDetailsImpl userDetails, Pageable pageable) {
        return new MypageMyBookMarkplantFinalDto(
                postRepository.myPlantBookMark(userDetails.getUserId(),pageable)
        );

    }

    //?????? ????????????
    public HashMap<String, String> inactivateAccount(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        user.changePassword(UUID.randomUUID().toString());
        user.changeUsername(UUID.randomUUID().toString());
        userRepository.save(user);
        return commUtils.responseHashMap(HttpStatus.OK);
    }



}
