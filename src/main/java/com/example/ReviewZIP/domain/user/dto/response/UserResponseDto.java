package com.example.ReviewZIP.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class UserResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserPreviewDto {
        private Long userId;
        private String nickname;
        private String name;
        private String profileUrl;
        private boolean following;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostPreviewListDto{
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
    public static class PostPreviewDto {
        private Long postId;
        private String postImageUrl;
        private Integer likeNum;
        private Integer scrabNum;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OtherUserInfoDto {
        private Long userId;
        private String name;
        private String nickname;
        private String profileUrl;
        private Integer postNum;
        private Integer followingNum;
        private Integer followerNum;
        private boolean following;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfoDto{
        private Long userId;
        private String name;
        private String nickname;
        private String profileUrl;
        private Integer postNum;
        private Integer followingNum;
        private Integer followerNum;
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HistoryDto{
        private Long historyId;
        private String type;
        private UserPreviewDto user;
        private String hashtag;
    }
}
