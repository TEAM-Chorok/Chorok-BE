package com.finalproject.chorok.post.service;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.post.dto.comment.CommentRequestDto;
import com.finalproject.chorok.post.dto.comment.CommentUpdateRequestDto;
import com.finalproject.chorok.post.model.Comment;
import com.finalproject.chorok.post.repository.CommentRepository;
import com.finalproject.chorok.post.utils.CommUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommUtils commUtils;


    // 댓글 등록
    @Transactional
    public void writeComment(User user, CommentRequestDto commentRequestDto) {
        Comment saveComment = new Comment(user,commUtils.getPost(commentRequestDto.getPostId()),commentRequestDto.getCommentContent());

        commentRepository.save(saveComment);
    }

    // 댓글 수정
    @Transactional
    public void updateComment(CommentUpdateRequestDto commentUpdateRequestDto,User user) throws IllegalAccessException {
        commUtils.commentAuthChk(commentUpdateRequestDto.getCommentId(),user.getUserId());
        Comment comment = commUtils.getComment(commentUpdateRequestDto.getCommentId());
        comment.update(commentUpdateRequestDto);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId,User user) throws IllegalAccessException {
        commUtils.commentAuthChk(commentId,user.getUserId());
        //commUtils.getComment(commentId);
        commentRepository.deleteById(commentId);
    }
}
