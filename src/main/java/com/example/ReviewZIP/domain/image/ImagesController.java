package com.example.ReviewZIP.domain.image;

import com.example.ReviewZIP.domain.image.dto.request.ImageRequestDto;
import com.example.ReviewZIP.domain.image.dto.response.ImageResponseDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/images")
public class ImagesController {
    private final ImagesService imageService;

    @PostMapping("/users/{userId}")
    public ApiResponse<ImageResponseDto.toUploadImageDto> uploadImage(@PathVariable(name="userId") Long userId, @RequestParam("file") MultipartFile file){
        ImageRequestDto imageRequestDto = new ImageRequestDto();
        imageRequestDto.setUserId(userId);
        imageRequestDto.setUrl(file.getOriginalFilename());

        Images image = imageService.uploadImage(file, userId);

        return ApiResponse.onSuccess(ImageConverter.toUploadImageDto(image));
    }
}
