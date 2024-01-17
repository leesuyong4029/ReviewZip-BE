package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.Follows;
import com.example.ReviewZIP.domain.user.dto.response.FollowsResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UsersConverter {
    public static FollowsResponseDto.FollowsPreviewDto followsPreviewDto(Follows follows){
        return FollowsResponseDto.FollowsPreviewDto.builder()
                .followerId(follows.getSender().getId())
                .nickname(follows.getSender().getNickname())
                .profileUrl(follows.getReceiver().getProfileUrl())
                .build();
    }

    public static FollowsResponseDto.FollowsPreviewListDto followsPreviewListDto(Page<Follows> followsList){
        List<FollowsResponseDto.FollowsPreviewDto> followsPreviewDtoList = followsList.stream()
                .map(UsersConverter::followsPreviewDto).collect(Collectors.toList());

        return FollowsResponseDto.FollowsPreviewListDto.builder()
                .isLast(followsList.isLast())
                .isFirst(followsList.isFirst())
                .totalElements(followsList.getTotalElements())
                .totalPage(followsList.getTotalPages())
                .listSize(followsPreviewDtoList.size())
                .followsList(followsPreviewDtoList)
                .build();
    }
}
