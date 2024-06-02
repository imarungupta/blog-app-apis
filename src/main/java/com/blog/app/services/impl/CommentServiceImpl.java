package com.blog.app.services.impl;

import com.blog.app.entities.Comment;
import com.blog.app.entities.Post;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payloads.CommentDto;
import com.blog.app.repositories.CommentRepository;
import com.blog.app.repositories.PostRepository;
import com.blog.app.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Comment mappedCommentDto = this.modelMapper.map(commentDto, Comment.class);
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        mappedCommentDto.setPost(post);

        Comment savedComment = this.commentRepository.save(mappedCommentDto);
        CommentDto mappedComment = this.modelMapper.map(savedComment, CommentDto.class);


        return mappedComment;
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
        return null;
    }

    @Override
    public CommentDto getAllComments() {
        return null;
    }

    @Override
    public CommentDto getSingleComment(Integer commentId) {
        return null;
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment id", commentId));
        this.commentRepository.delete(comment);
    }
}
