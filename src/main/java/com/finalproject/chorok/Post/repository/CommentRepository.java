package com.finalproject.chorok.Post.repository;

import com.finalproject.chorok.Post.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    // 나중에 profileImgUrl 추가해야함
    @Query(nativeQuery = true,value = "SELECT" +
            "comment_id a,comment_content a," +
    //        "profileImgUrl b" +
            "nick_name b FROM comment a, ")
    List<Comment> findByPostPostId(Long postId);

}
