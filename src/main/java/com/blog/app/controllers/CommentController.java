package com.blog.app.controllers;

import com.blog.app.payloads.ApiResponse;
import com.blog.app.payloads.CommentDto;
import com.blog.app.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> creatComment(@RequestBody CommentDto commentDto, @PathVariable("postId") Integer postId){

        CommentDto comment = this.commentService.createComment(commentDto, postId);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @PostMapping("/post/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment( @PathVariable("commentId") Integer commentId){

        this.commentService.deleteComment(commentId);

        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully",true),HttpStatus.OK);
    }
}
