package com.example.ReviewZIP.domain.post.dto.response;

import com.example.ReviewZIP.domain.image.dto.response.ImageResponseDto;
import com.example.ReviewZIP.domain.postHashtag.dto.response.PostHashtagResponseDto;
import com.example.ReviewZIP.domain.store.dto.request.StoreRequestDto;
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
        private List<PostHashtagResponseDto.HashtagDto> hashtags;
        private List<ImageResponseDto.ImageDto> postImages;
        private StoreRequestDto.StoreInfoDto store;
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
