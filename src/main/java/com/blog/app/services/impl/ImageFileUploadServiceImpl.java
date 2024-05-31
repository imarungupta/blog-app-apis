package com.blog.app.services.impl;

import com.blog.app.services.ImageFileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ImageFileUploadServiceImpl implements ImageFileUploadService {
    @Override
    public String uploadImageFile(MultipartFile multipartFile, String path) throws IOException {

        // Step1- Get the file name from the MultipartFile
        String fileName = multipartFile.getOriginalFilename();
        System.out.println("File Name:-"+fileName);
        // Step2- Create a full path location till file
        String fullPath = path + File.separator+fileName;

        System.out.println("fullPath:-"+fullPath);
        // Create the images folder if not created
        File filePath = new File(fullPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        // Upload or copy the image from source(multipart file) to created folder(filePath) using fullPath
        Files.copy(multipartFile.getInputStream(), Paths.get(fullPath));

        // Return the file name which is getting uploaded.


        return fileName;
    }
}
