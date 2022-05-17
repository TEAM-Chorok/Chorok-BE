package com.finalproject.chorok.mypage.service;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.mypage.model.PlantBookMark;
import com.finalproject.chorok.mypage.repository.PlantBookMarkRepository;
import com.finalproject.chorok.post.model.PostBookMark;
import com.finalproject.chorok.post.repository.PostRepository;
import com.finalproject.chorok.post.utils.CommUtils;
import com.finalproject.chorok.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final PlantBookMarkRepository plantBookMarkRepository;
    private final CommUtils commUtils;
    private final PostRepository postRepository;

    // 식물 북마크
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
    // 내가 북마크한 게시물
    public void myPostBookMark(UserDetailsImpl userDetails) {
       // p
    }

    public void myPhoto(UserDetailsImpl userDetails) {
    }


}
