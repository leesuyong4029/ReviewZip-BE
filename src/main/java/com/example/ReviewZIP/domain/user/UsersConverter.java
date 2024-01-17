package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.user.dto.response.OtherInfoResDto;

public class UsersConverter {
    public static OtherInfoResDto toOtherInfoDto(Users user, Integer followingNum, Integer followerNum){

        String imageUrl = (user.getProfileUrl() != null) ? user.getProfileUrl() : null;

        return OtherInfoResDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .imageUrl(imageUrl)
                .followingNum(followingNum)
                .followerNum(followerNum)
                .build();
    }
}
