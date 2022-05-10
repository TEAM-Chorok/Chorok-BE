package com.finalproject.chorok.post.repository;

import com.finalproject.chorok.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
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
public interface PostRepository extends JpaRepository<Post,Long> {
    // 게시판 타입별 게시물 전체조회
    List<Post> findAllByPostTypePostTypeCodeOrderByCreatedAt(String postTypeCode);
    // 게시판 타입과 식물위치로 게시물 전체조회
    List<Post> findAllByPostTypePostTypeCodeAndPlantPlaceCodeOrderByCreatedAt(String postTypeCode, String plantPlaceCode);

}
