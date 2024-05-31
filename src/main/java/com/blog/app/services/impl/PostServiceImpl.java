package com.blog.app.services.impl;

import com.blog.app.entities.Category;
import com.blog.app.entities.Post;
import com.blog.app.entities.User;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payloads.PostDto;
import com.blog.app.payloads.PostPageResponse;
import com.blog.app.repositories.CategoryRepository;
import com.blog.app.repositories.PostRepository;
import com.blog.app.repositories.UserRepository;
import com.blog.app.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        System.out.println("inside create post service method ");
        Post dtoToPost = this.modelMapper.map(postDto, Post.class);

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        dtoToPost.setImage("default.png");
        dtoToPost.setAddedDate(new Date());
        dtoToPost.setUser(user);
        dtoToPost.setCategory(category);

        Post saveToDB = this.postRepository.save(dtoToPost);
        PostDto postToDto = this.modelMapper.map(saveToDB, PostDto.class);

        return postToDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post dtoToPost = this.modelMapper.map(postDto, Post.class);
        Post postFromDB = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

       // postFromDB.setCategory(dtoToPost.getCategory());
        postFromDB.setPostTitle(dtoToPost.getPostTitle());
        postFromDB.setImage(dtoToPost.getImage());
        postFromDB.setContent(dtoToPost.getContent());
        //postFromDB.setAddedDate(dtoToPost.getAddedDate());

        Post updatedPost = postRepository.save(postFromDB);

        return this.modelMapper.map(updatedPost,PostDto.class);
    }


    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        this.postRepository.delete(post);
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> all = this.postRepository.findAll();
        return all.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getAllPostPageWise(Integer pageSize, Integer pageNumber) {

        /*Integer pageSize   = 5;
        Integer pageNumber =2;*/
        Pageable pageable = PageRequest.of(pageSize,pageNumber);
        Page<Post> allPagePost = this.postRepository.findAll(pageable);
        List<Post> allPosts = allPagePost.getContent();

        List<PostDto> allPostDto = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return allPostDto;
    }

    @Override
    public PostPageResponse getAllPostResponsePageWise(Integer pageNumber, Integer pageSize, String sortBy,String sortDir) {
//        System.out.println("sortDir:-"+sortDir);
//        Sort sort=null;
//
//        if(sortDir.equalsIgnoreCase("asc")){
//            sort = Sort.by(sortBy).ascending();
//        }else{
//            sort = Sort.by(sortBy).descending();
//        } OR : Ternary Operator

        Sort sortDirection = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortDirection);

        Page<Post> allPagePost = this.postRepository.findAll(pageable);
        List<Post> allPosts = allPagePost.getContent();
        List<PostDto> postDtos = allPosts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostPageResponse postPageResponse = new PostPageResponse();

        postPageResponse.setContent(postDtos);
        postPageResponse.setPageNumber(allPagePost.getNumber());
        postPageResponse.setPageSize(allPagePost.getSize());
        postPageResponse.setTotalPages(allPagePost.getTotalPages());
        postPageResponse.setTotalElements((int) allPagePost.getTotalElements());
        postPageResponse.setLastPage(allPagePost.isLast());

        return postPageResponse;
    }

    @Override
    public PostDto getSinglePost(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        PostDto postDto = this.modelMapper.map(post, PostDto.class);

        return postDto;
    }

    @Override
    public List<PostDto> getAllPostByUser(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        List<Post> postByUser = this.postRepository.findPostByUser(user);
        List<PostDto> postDtos = postByUser.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> getAllPostByCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        List<Post> postByCategory = this.postRepository.findPostByCategory(category);
        List<PostDto> postDtos = postByCategory.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> searchPostsUsingHibernateQuery(String title) {
        List<Post> postsByTitleContaining = this.postRepository.searchPostByTitle("%"+title+"%");
        List<PostDto> postDtos = postsByTitleContaining.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> searchPostsWithoutHibernateQuery(String title) {
        List<Post> postsByTitleContaining = this.postRepository.searchPostByTitle(title);
        List<PostDto> postDtos = postsByTitleContaining.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }
}


