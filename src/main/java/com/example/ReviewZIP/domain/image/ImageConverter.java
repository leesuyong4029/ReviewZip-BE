package com.example.ReviewZIP.domain.image;

import com.example.ReviewZIP.domain.image.dto.response.ImageResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImageConverter {

    public static ImageResponseDto.ImageDto toImageDto(Images image){
        return ImageResponseDto.ImageDto.builder()
                .imageId(image.getId())
                .imageUrl(image.getUrl())
                .build();
    }
    public static List<ImageResponseDto.ImageDto> toUploadImageDto(List<Images> imagesList) {
        return imagesList.stream().map(ImageConverter::toImageDto).collect(Collectors.toList());
    }
}
