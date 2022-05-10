package com.finalproject.chorok.post.repository;

import com.finalproject.chorok.post.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    @Query(value = "SELECT * FROM post_like a WHERE a.user_id=:userId AND a.post_id =:postId",nativeQuery = true)
    PostLike findUserLikeQuery(@Param("userId") Long userId, @Param("postId") Long postId);

    @Modifying
    @Query(value = "DELETE FROM post_like WHERE user_id=:userId And post_id=:postId",nativeQuery = true)
    void deleteByUser_UserIdAndPost_PostId(@Param("userId") Long userId, @Param("postId") Long postId);
}
