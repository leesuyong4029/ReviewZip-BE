package com.example.ReviewZIP.domain.postLike.dto.request;


import lombok.Getter;

public class PostLikesRequestDto {

    @Getter
    public static class PostLikesDto {
        private Long postId;
        private Long userId;
    }
}

