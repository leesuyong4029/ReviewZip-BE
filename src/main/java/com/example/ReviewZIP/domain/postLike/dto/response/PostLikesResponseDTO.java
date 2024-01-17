package com.example.ReviewZIP.domain.postLike.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostLikesResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostLikesResultDTO {
        private Long id;
        private Long postId;
        private Long userId;
        private Integer count;
    }
}
