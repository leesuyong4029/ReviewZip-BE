package com.example.ReviewZIP.domain.post.dto.response;

import lombok.*;

import java.util.List;


public class PostResponseDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatedPostResponseDto {
        private Long postId;
        private Long userId;
        private String comment;
        private Double point;
        private List<Long> imageIds;
    }
}