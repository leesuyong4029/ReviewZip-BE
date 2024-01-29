package com.example.ReviewZIP.domain.post.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostPreviewDto {
        private Long id;
        private String imageUrl;
        private Integer likeNum;
        private Integer scrabNum;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfoDto{
        private Long id;
        private String nickname;
        private String profileUrl;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class ImageListDto{
        private Long id;
        private String url;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostPreviewListDto {
        private List<PostPreviewDto> postList;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostInfoDto{
        private Long postId;
        private String comment;
        private Double point;
        private Integer likeNum;
        private boolean checkLike;
        private boolean checkScrab;
        private LocalDateTime createdAt;
        private UserInfoDto userInfo;
        private List<String> hashtags;
        private List<ImageListDto> postImages;
    }

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
