package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.Follows;
import com.example.ReviewZIP.domain.user.dto.response.FollowsResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UsersConverter {
    public static FollowsResponseDto.FollowingPreviewDto followingPreviewDto(Follows follows){
        return FollowsResponseDto.FollowingPreviewDto.builder()
                .followingId(follows.getReceiver().getId())
                .nickname(follows.getReceiver().getNickname())
                .profileUrl(follows.getReceiver().getProfileUrl())
                .build();
    }

    public static FollowsResponseDto.FollowingPreviewListDto followingPreviewListDto(Page<Follows> followsList){
        List<FollowsResponseDto.FollowingPreviewDto> followsPreviewDtoList = followsList.stream()
                .map(UsersConverter::followingPreviewDto).collect(Collectors.toList());

        return FollowsResponseDto.FollowingPreviewListDto.builder()
                .isLast(followsList.isLast())
                .isFirst(followsList.isFirst())
                .totalElements(followsList.getTotalElements())
                .totalPage(followsList.getTotalPages())
                .listSize(followsPreviewDtoList.size())
                .followsList(followsPreviewDtoList)
                .build();
    }
}
