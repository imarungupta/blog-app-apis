package com.blog.app.controllers;

import com.blog.app.payloads.ApiResponse;
import com.blog.app.payloads.PostDto;
import com.blog.app.payloads.PostPageResponse;
import com.blog.app.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.blog.app.config.ApiConstants.PAGE_NUMBER;
import static com.blog.app.config.ApiConstants.PAGE_SIZE;

@RestController
@RequestMapping("/api/")
public class PostController {
    @Autowired
    private PostService postService;
    // Create Post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable Integer categoryId,
                                              @PathVariable Integer userId){

        PostDto post = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(post, HttpStatus.CREATED);
    }
    // Get Entire Posts
    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts(){

        List<PostDto> allPost = this.postService.getAllPost();

        return new ResponseEntity<List<PostDto>>(allPost,HttpStatus.OK);
    }
    // Get Entire Posts according pageSize and pageNumber i.e. pageNation
    @GetMapping("/posts/pageWise")
    public ResponseEntity<List<PostDto>> getAllPostsPagination(@RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER, required = false) Integer pageNumber,
                                                  @RequestParam (value= "pageSize", defaultValue = PAGE_SIZE, required = false) Integer pageSize){

        List<PostDto> allPost = this.postService.getAllPostPageWise(pageNumber,pageSize);

        return new ResponseEntity<List<PostDto>>(allPost,HttpStatus.OK);
    }


    @GetMapping("/posts/completeResponsePageWise")
    public ResponseEntity<PostPageResponse> getAllPostsResponsePagination(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                          @RequestParam (value= "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                                          @RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy,
                                                                          @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir){


        PostPageResponse allPost = this.postService.getAllPostResponsePageWise(pageNumber, pageSize, sortBy,sortDir);

        return new ResponseEntity<PostPageResponse>(allPost,HttpStatus.OK);
    }

    // Get All Post done by particular user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
        List<PostDto> allPostByUser = this.postService.getAllPostByUser(userId);

        return new ResponseEntity<>(allPostByUser,HttpStatus.OK);
    }
    // Get All Post By Category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
        return new ResponseEntity<>(this.postService.getAllPostByCategory(categoryId),HttpStatus.OK);
    }
    // Get Post by post id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable Integer postId){
        PostDto singlePost = this.postService.getSinglePost(postId);
        return new ResponseEntity<>(singlePost,HttpStatus.OK);
    }
    // Delete Post
    @DeleteMapping("/posts/{postId}")
    private ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully",true),HttpStatus.OK);
    }

    // Search by title keyword
    @GetMapping("/posts/searchByQuery/{title}")
    private ResponseEntity<List<PostDto>> searchByTitleContainingWithHibernateQuery(@PathVariable String title){
        List<PostDto> postDtos = this.postService.searchPostsUsingHibernateQuery(title);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

    @GetMapping("/posts/search/{title}")
    private ResponseEntity<List<PostDto>> searchByTitleContaining(@PathVariable String title){
        List<PostDto> postDtos = this.postService.searchPostsWithoutHibernateQuery(title);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }
}
