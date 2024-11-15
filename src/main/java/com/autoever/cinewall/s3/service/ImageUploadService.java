package com.autoever.cinewall.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageUploadService {
    private final AmazonS3 s3Client;


    @Value("${s3.bucket}")
    private String bucket;

    public String upload(MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename();
        String filename = changeFileName(originalFilename);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(image.getSize());

        s3Client.putObject(bucket, filename, image.getInputStream(), metadata);
        return s3Client.getUrl(bucket, filename).toString();
    }

    // 파일 이름을 UUID로 변경하는 메서드
    private String changeFileName(String originalFilename) {
        String extension = "";
        int dotIndex = originalFilename.lastIndexOf(".");
        if (dotIndex > 0) {
            extension = originalFilename.substring(dotIndex);  // 확장자가 있을 경우만 처리
        }
        return UUID.randomUUID().toString() + extension;  // 확장자를 추가한 새로운 파일명
    }
}
