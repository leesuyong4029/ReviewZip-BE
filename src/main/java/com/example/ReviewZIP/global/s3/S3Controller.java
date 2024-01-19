package com.example.ReviewZIP.global.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;

    /**
     * Amazon S3에 파일 업로드
     */
    @PostMapping("/upload")
    public void uploadFile(@RequestPart List<MultipartFile> multipartFile) {
        s3Service.uploadFile(multipartFile);
    }

    /**
     * Amazon S3에 업로드 된 파일을 삭제
     * @return 성공 시 200 Success
     */
    @DeleteMapping("/delete")
    public void deleteFile( @RequestParam String fileName) {
        s3Service.deleteFile(fileName);
    }
}
