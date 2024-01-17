package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.user.dto.response.OtherInfoResponseDto;

public class UsersConverter {
    public static OtherInfoResponseDto toOtherInfoDto(Users user, Integer followingNum, Integer followerNum){

        String imageUrl = (user.getProfileUrl() != null) ? user.getProfileUrl() : null;

        return OtherInfoResponseDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .profileUrl(imageUrl)
                .followingNum(followingNum)
                .followerNum(followerNum)
                .build();
    }
}
