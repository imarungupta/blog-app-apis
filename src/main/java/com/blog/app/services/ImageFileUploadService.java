package com.blog.app.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageFileUploadService {
    String uploadImageFile(MultipartFile file, String path) throws IOException;
}
