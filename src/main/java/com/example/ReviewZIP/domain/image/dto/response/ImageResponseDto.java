package com.example.ReviewZIP.domain.image.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


public class ImageResponseDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageListDto {
        private List<Long> imageIds;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class ImageDto{
        private Long imageId;
        private String imageUrl;
    }

}
