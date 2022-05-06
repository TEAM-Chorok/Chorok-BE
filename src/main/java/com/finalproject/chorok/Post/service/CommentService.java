package com.finalproject.chorok.Post.service;

import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.Post.dto.CommentRequestDto;
import com.finalproject.chorok.Post.dto.CommentUpdateRequestDto;
import com.finalproject.chorok.Post.model.Comment;
import com.finalproject.chorok.Post.repository.CommentRepository;
import com.finalproject.chorok.Post.utils.CommUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommUtils commUtils;


    // 댓글 등록
    public Comment writeComment(User user, CommentRequestDto commentRequestDto) {
        Comment saveComment = new Comment(user,commUtils.getPost(commentRequestDto.getPostId()),commentRequestDto.getCommentContent());

        return commentRepository.save(saveComment);
    }

    public void updateComment(CommentUpdateRequestDto commentUpdateRequestDto) {
        Comment comment = commUtils.getComment(commentUpdateRequestDto.getCommentNo());
        comment.update(commentUpdateRequestDto);
    }
}
