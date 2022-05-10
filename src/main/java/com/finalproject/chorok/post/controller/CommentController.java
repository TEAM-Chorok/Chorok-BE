package com.finalproject.chorok.post.controller;

import com.finalproject.chorok.post.dto.CommentRequestDto;
import com.finalproject.chorok.post.dto.CommentUpdateRequestDto;
import com.finalproject.chorok.post.service.CommentService;
import com.finalproject.chorok.post.utils.CommUtils;
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
    private final CommUtils commUtils;

    // 댓글 등록
    @PostMapping("/write-comment")
    public ResponseEntity<?> writeComment (
            @Valid @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    )  {
        commentService.writeComment(userDetails.getUser(),commentRequestDto);
        return  ResponseEntity.status(HttpStatus.OK).body(commUtils.responseHashMap(HttpStatus.OK));
    }
    
    // 댓글 수정
    @PutMapping("/update-comment")
    public ResponseEntity<?> updateComment(@Valid @RequestBody CommentUpdateRequestDto commentUpdateRequestDto){
         commentService.updateComment(commentUpdateRequestDto);
         return  ResponseEntity.status(HttpStatus.OK).body(commUtils.responseHashMap(HttpStatus.OK));
    }
}
