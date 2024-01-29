package com.example.ReviewZIP.domain.image;

import com.example.ReviewZIP.domain.image.dto.response.UploadImageResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImageConverter {
    public static UploadImageResponseDto toUploadImageDto(List<Images> imagesList) {
        List<Long> imageIds = imagesList.stream().map(Images::getId).collect(Collectors.toList());
        return new UploadImageResponseDto(imageIds);
    }
}
