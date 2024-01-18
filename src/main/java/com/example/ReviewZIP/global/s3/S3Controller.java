package com.example.ReviewZIP.global.s3;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<Long> upload(@RequestPart MultipartFile file) {
        Long imageId = s3Service.uploadFile(file);
        return new ResponseEntity<>(imageId, HttpStatus.OK);
    }
}
