package com.finalproject.chorok.post.repository;

import com.finalproject.chorok.post.dto.comment.CommentResponseDto;
import com.finalproject.chorok.post.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query(nativeQuery = true,value = "SELECT a.post_id, a.comment_id, a.comment_content, b.profile_img_url," +
            "b.nickname,a.created_at,a.modified_at FROM comment a ,user b" +
            " WHERE a.user_id = b.user_id AND a.post_id=:postId ORDER BY created_at asc;")
    List<CommentResponseDto>  findCommentToPostIdQuery(@Param("postId") Long postId);

}
