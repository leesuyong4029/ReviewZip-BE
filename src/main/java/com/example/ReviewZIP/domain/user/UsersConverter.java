package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.Follows;
import com.example.ReviewZIP.domain.user.dto.response.FollowsResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UsersConverter {
    public static FollowsResponseDto.FollowingPreviewDto toFollowingPreviewDto(Follows follows){
        return FollowsResponseDto.FollowingPreviewDto.builder()
                .followingId(follows.getReceiver().getId())
                .profileUrl(follows.getReceiver().getProfileUrl())
                .nickname(follows.getReceiver().getNickname())
                .build();
    }

    public static FollowsResponseDto.FollowingPreviewListDto toFollowingPreviewListDto(Page<Follows> followsList){
        List<FollowsResponseDto.FollowingPreviewDto> followingPreviewDtoList = followsList.stream()
                .map(UsersConverter::toFollowingPreviewDto).collect(Collectors.toList());

        return FollowsResponseDto.FollowingPreviewListDto.builder()
                .isLast(followsList.isLast())
                .isFirst(followsList.isFirst())
                .totalElements(followsList.getTotalElements())
                .totalPage(followsList.getTotalPages())
                .listSize(followingPreviewDtoList.size())
                .followsList(followingPreviewDtoList)
                .build();
    }
}
