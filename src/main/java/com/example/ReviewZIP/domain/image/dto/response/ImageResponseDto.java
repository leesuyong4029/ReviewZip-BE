package com.example.ReviewZIP.domain.image.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ImageResponseDto {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class ImageDto{
        private Long imageId;
        private String imageUrl;
    }
}
