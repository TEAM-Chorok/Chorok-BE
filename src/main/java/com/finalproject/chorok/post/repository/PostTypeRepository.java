package com.finalproject.chorok.post.repository;

import com.finalproject.chorok.post.model.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTypeRepository extends JpaRepository<PostType,String> {
    @Query(nativeQuery = true,value = "SELECT post_type_code FROM post_type WHERE post_Type_code in ('postType02','postType03','postType04')")
    List<String> findByAllCommunityQuery();

    @Query(nativeQuery = true, value = "SELECT post_type_code FROM post_type WHERE post_Type_code =:postTypeCode")
    List<String> findAllByPostTypeCodeQuery(String postTypeCode);
}
