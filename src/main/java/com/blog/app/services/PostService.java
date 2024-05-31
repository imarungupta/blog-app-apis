package com.blog.app.services;

import com.blog.app.payloads.PostDto;
import com.blog.app.payloads.PostPageResponse;

import java.util.List;

public interface PostService {
    // Create
    PostDto createPost(PostDto postDto, Integer userid, Integer categoryId);
    // Update
   // PostDto updatePostWithUserIdAndCategoryIdAndPostId(PostDto postDto, Integer postId);
    PostDto updatePost(PostDto postDto, Integer postId);
    //Delete
    void deletePost(Integer postId);
    // getAll
    List<PostDto> getAllPost();
    // getAll PageWise
    List<PostDto> getAllPostPageWise(Integer pageNumber, Integer pageSize);
    // getAll
    PostPageResponse getAllPostResponsePageWise(Integer pageNumber, Integer pageSize, String sortBy,String sortDir);
    // get Single Post
    PostDto getSinglePost(Integer postId);
    // Get all post by user
    List<PostDto> getAllPostByUser(Integer userId);
    // Get all post by category
    List<PostDto> getAllPostByCategory(Integer categoryId);
    // search post by keyword
    List<PostDto> searchPostsUsingHibernateQuery(String keyword);
    // search post by keyword
    List<PostDto> searchPostsWithoutHibernateQuery(String keyword);
}
