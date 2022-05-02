package com.finalproject.chorok.Post.controller;

import com.finalproject.chorok.Post.dto.CommentRequestDto;
import com.finalproject.chorok.Post.model.Comment;
import com.finalproject.chorok.Post.service.CommentService;
import com.finalproject.chorok.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/write-comment")
    public Comment writeComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
         //  @PathVariable Long postId,
           @RequestBody CommentRequestDto commentRequestDto
    ){
        System.out.println(commentRequestDto.getPostId());
        System.out.println(commentRequestDto.getCommentContent());
        return commentService.writeComment(userDetails.getUser(),commentRequestDto);
    }


}
