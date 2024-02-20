package com.example.ReviewZIP.domain.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class PostResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfoDto{
        private Long userId;
        private String name;
        private String nickname;
        private String profileUrl;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class ImageDto{
        private Long imageId;
        private String imageUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HashtagDto{
        private Long hashtagId;
        private String tagName;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoreInfoDto{
        private String latitude;
        private String longitude;
        private String name;
        private String roadAddressName;
        private String addressName;
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
        private Integer scrabNum;
        private Integer hashtagNum;
        private boolean checkLike;
        private boolean checkScrab;
        private boolean checkMine;
        private String createdAt;
        private UserInfoDto user;
        private List<HashtagDto> hashtags;
        private List<ImageDto> postImages;
        private StoreInfoDto store;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostUserLikeDto{
        private Long userId;
        private String name;
        private String nickname;
        private String profileUrl;
        private boolean following;
    }
}
