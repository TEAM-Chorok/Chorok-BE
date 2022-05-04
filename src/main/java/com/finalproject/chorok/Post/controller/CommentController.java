package com.finalproject.chorok.Post.controller;

import com.finalproject.chorok.Post.dto.CommentRequestDto;
import com.finalproject.chorok.Post.service.CommentService;
import com.finalproject.chorok.Post.utils.ResponseUtils;
import com.finalproject.chorok.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ResponseUtils responseUtils;

    @PostMapping("/write-comment")
    public ResponseEntity<?> writeComment (
            @Valid @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    )  {
        commentService.writeComment(userDetails.getUser(),commentRequestDto);
        return  ResponseEntity.status(HttpStatus.OK).body(responseUtils.responseHashMap(HttpStatus.OK));
    }

}
