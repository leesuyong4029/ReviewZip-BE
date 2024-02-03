package com.example.ReviewZIP.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class FollowResponseDto {

    // 팔로잉 목록 프리뷰 dto
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FollowingPreviewListDto{
        private List<FollowingPreviewDto> followsList;
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
    public static class FollowingPreviewDto{
        private Long followingId;
        private String profileUrl;
        private String nickname;
    }

    // 팔로워 목록 프리뷰 dto
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FollowerPreviewListDto{
        private List<FollowerPreviewDto> followsList;
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
    public static class FollowerPreviewDto{
        private Long followerId;
        private String profileUrl;
        private String nickname;
    }
}