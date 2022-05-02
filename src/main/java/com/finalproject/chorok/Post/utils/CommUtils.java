package com.finalproject.chorok.Post.utils;

import com.finalproject.chorok.Post.model.Post;
import com.finalproject.chorok.Post.model.PostType;
import com.finalproject.chorok.Post.repository.PostRepository;
import com.finalproject.chorok.plant.model.PlantPlace;
import com.finalproject.chorok.plant.repository.PlantPlaceRepository;
import com.finalproject.chorok.Post.repository.PostTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommUtils {
    private final PlantPlaceRepository plantPlaceRepository;
    private final PostTypeRepository postTypeRepository;
    private final PostRepository postRepository;


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
}
