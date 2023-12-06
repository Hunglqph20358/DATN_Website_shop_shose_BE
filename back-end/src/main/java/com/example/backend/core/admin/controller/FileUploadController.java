package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.service.FileUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class FileUploadController {
    @Autowired
    private FileUpload fileUpload;

    @GetMapping("/upload")
    public String uploadFile(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        String imageURL = fileUpload.uploadFile(multipartFile);
        return imageURL;
    }
}
