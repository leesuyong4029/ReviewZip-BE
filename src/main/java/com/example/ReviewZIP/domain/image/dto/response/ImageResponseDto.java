package com.example.ReviewZIP.domain.image.dto.response;

import lombok.*;

import java.util.List;

public class ImageResponseDto {
    @Builder
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageUploadListDto {
        private List<Long> imageIds;
    }
}
