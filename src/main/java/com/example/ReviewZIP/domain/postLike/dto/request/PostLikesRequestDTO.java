package com.example.ReviewZIP.domain.postLike.dto.request;

import lombok.Getter;

public class PostLikesRequestDTO {

    @Getter
    public static class PostLikesDTO {
        private Long postId;
        private Long userId;
    }
}

