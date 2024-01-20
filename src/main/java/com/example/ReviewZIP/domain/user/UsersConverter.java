package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;

public class UsersConverter {
    public static UserResponseDto.OtherInfoDto toOtherInfoDto(Users user, Integer followingNum, Integer followerNum, boolean isFollowing){

        String imageUrl = (user.getProfileUrl() != null) ? user.getProfileUrl() : null;

        return UserResponseDto.OtherInfoDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .profileUrl(imageUrl)
                .followingNum(followingNum)
                .followerNum(followerNum)
                .isFollowing(isFollowing)
                .build();
    }
}
