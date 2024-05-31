package com.blog.app.services;

import com.blog.app.payloads.CommentDto;

public interface CommentService {

    // create
    CommentDto createComment(CommentDto commentDto);

    // update
    CommentDto updateComment(CommentDto commentDto, Integer commentId);

    // get All Comments
    CommentDto getAllComments();

    // Get single comment
    CommentDto getSingleComment(Integer commentId);

    // Delete comment
    void deleteComment(Integer commentId);

}
