package com.blog.app.controllers;

import com.blog.app.payloads.PostDto;
import com.blog.app.services.ImageFileUploadService;
import com.blog.app.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/")
public class ImageFileUploadController {

    @Autowired
    private ImageFileUploadService imageFileUploadService;
    @Autowired
    private PostService postService;

    @Value("${profile.image}")
    private String imagePath;

    @PostMapping("/post/image/upload/{postId}")
    private ResponseEntity<PostDto> imageUpload(@RequestParam("imageFile") MultipartFile imageFile,
                                                @PathVariable Integer postId) throws IOException {
        // The imageFile will be defined as name or id in the form input tag

        PostDto singlePostDto = this.postService.getSinglePost(postId);

        String uploadImageFile = this.imageFileUploadService.uploadImageFile(imageFile, imagePath);

        singlePostDto.setImage(uploadImageFile);

        PostDto updatePost = this.postService.updatePost(singlePostDto, postId);

        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }
}
