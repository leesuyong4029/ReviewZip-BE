package com.example.ReviewZIP.domain.image;

import com.example.ReviewZIP.domain.image.dto.response.ImageUploadResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImageConverter {
    public static ImageUploadResponseDto toUploadImageDto(List<Images> imagesList) {
        List<Long> imageIds = imagesList.stream().map(Images::getId).collect(Collectors.toList());
        return new ImageUploadResponseDto(imageIds);
    }
}
