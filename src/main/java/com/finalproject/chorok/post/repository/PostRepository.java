package com.finalproject.chorok.post.repository;

import com.finalproject.chorok.post.model.Post;
import com.finalproject.chorok.post.repository.querydsl.PostRepositoryImpl;
import com.finalproject.chorok.post.repository.querydsl.PostRepositoryQueryDsl;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * [Repository] - 게시판 Repository
 *
 * @class   : PostRepository
 * @author  : 김주호
 * @since   : 2022.04.30
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Repository
public interface PostRepository extends JpaRepository<Post,Long>, PostRepositoryQueryDsl {
/*
    // 플랜테리어 -  타입별 게시물 전체조회
    List<Post> findAllByPostTypePostTypeCodeOrderByCreatedAtDesc(String postTypeCode);

    // 플랜테리어 - 타입과 식물위치로 게시물 전체조회
    List<Post> findAllByPostTypePostTypeCodeAndPlantPlaceCodeOrderByCreatedAtDesc(String postTypeCode, String plantPlaceCode);
*/

    // 커뮤니티 타입 게시물 전체 조회
    List<Post> findByPostTypePostTypeCodeInOrderByCreatedAt(List<String> PostTypeCode);

    // 커뮤니티 - 게시판 타입 전체 조회
    List<Post> findByPostTypePostTypeCode(String postTypeCode);

/*
    // 플랜테리어 - 검색
    @Query(nativeQuery = true,
            value = "SELECT * FROM post WHERE post.post_type_code='postType01' AND post_title like %:keyword% OR post_content like  %:keyword% ORDER BY created_at DESC LIMIT 6")
    List<Post> plantariaSearchQuery(@Param("keyword") String keyword);
*/

    // 플랜테리어 - 검색- count
    @Query(nativeQuery = true,
            value = "SELECT count(*) FROM post WHERE post.post_type_code='postType01' AND post_title like %:keyword% OR post_content like %:keyword%")
    int plantariaSearchCountQuery(@Param("keyword") String keyword);

    // 플랜테리어 - 검색 -사진
    @Query(nativeQuery = true,
            value = "SELECT * FROM post WHERE post.post_type_code ='postType01' AND post_title like %:keyword% OR post_content like %:keyword%")
    List<Post> plantariaSearchPhotoQuery( @Param("keyword") String keyword);

    // 플랜테리어 - 검색 -사진 - 식물위치
    @Query(nativeQuery = true,
            value = "SELECT * FROM post WHERE post.post_type_code ='postType01' AND post.plant_place_code =:plantPlaceCode AND post_title like %:keyword% OR post_content like %:keyword%")
    List<Post> plantariaSearchPhotoToPostTypeCodeQuery( @Param("keyword") String keyword,@Param("plantPlaceCode")String plantPlaceCode);

    // 플랜테리어 - 검색 -사진 - 식물위치 - count
    @Query(nativeQuery = true,
            value = "SELECT count(*) FROM post WHERE post.post_type_code='postType01'AND post.plant_place_code =:plantPlaceCode AND post_title like %:keyword% OR post_content like %:keyword%")
    int plantariaSearchPhotoToPostTypeCodeCountQuery(@Param("keyword") String keyword,@Param("plantPlaceCode")String plantPlaceCode);



}