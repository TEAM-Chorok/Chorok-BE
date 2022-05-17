package com.finalproject.chorok.post.repository;

import com.finalproject.chorok.post.model.PostBookMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostBookMarkRepository extends JpaRepository<PostBookMark,Long> {
    @Query(value = "SELECT * FROM post_book_mark a WHERE a.user_id=:userId AND a.post_id =:postId",nativeQuery = true)
    PostBookMark findUserBookMarkQuery(@Param("userId") Long userId, @Param("postId") Long postId);

    @Modifying
    @Query(value = "DELETE FROM post_book_mark WHERE user_id=:userId And post_id=:postId",nativeQuery = true)
    void deleteByUserBookMarkQuery(@Param("userId") Long userId, @Param("postId") Long postId);

}
