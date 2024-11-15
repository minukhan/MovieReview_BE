package com.autoever.cinewall.s3.controller;

import ch.qos.logback.core.model.Model;
import com.autoever.cinewall.s3.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinewall/auth")
public class ImageUploadController {
    private final ImageUploadService imageUploadService;


    @PostMapping("/imageUpload")
    public String upload(MultipartFile image, Model model) throws IOException {
        String imageUrl = imageUploadService.upload(image);
        return imageUrl;
    }
}
