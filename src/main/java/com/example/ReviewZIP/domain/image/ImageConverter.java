package com.example.ReviewZIP.domain.image;

import com.example.ReviewZIP.domain.image.dto.response.ImageResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ImageConverter {
    public static ImageResponseDto.toUploadImageDto toUploadImageDto(Images image) {
        return ImageResponseDto.toUploadImageDto.builder()
                .imageId(image.getId())
                .build();
    }
}
