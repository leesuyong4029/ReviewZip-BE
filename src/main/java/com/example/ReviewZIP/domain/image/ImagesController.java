package com.example.ReviewZIP.domain.image;

import com.example.ReviewZIP.domain.image.dto.response.UploadImageResponseDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/images")
public class ImagesController {
    private final ImagesService imageService;

    @PostMapping("/users/{userId}")
    public ApiResponse<UploadImageResponseDto> uploadImage(@PathVariable(name="userId") Long userId, @RequestParam("fileList") List<MultipartFile> fileList){
        List<Images> imageList = imageService.uploadImage(fileList, userId);
        UploadImageResponseDto imageResponseDto = ImageConverter.toUploadImageDto(imageList);
        return ApiResponse.onSuccess(imageResponseDto);
    }
}
