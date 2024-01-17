package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.Follows;
import com.example.ReviewZIP.domain.user.dto.response.FollowsResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UsersConverter {
    public static FollowsResponseDto.FollowerPreviewDto followsPreviewDto(Follows follows){
        return FollowsResponseDto.FollowerPreviewDto.builder()
                .followerId(follows.getSender().getId())
                .nickname(follows.getSender().getNickname())
                .profileUrl(follows.getReceiver().getProfileUrl())
                .build();
    }

    public static FollowsResponseDto.FollowerPreviewListDto followsPreviewListDto(Page<Follows> followsList){
        List<FollowsResponseDto.FollowerPreviewDto> followsPreviewDtoList = followsList.stream()
                .map(UsersConverter::followsPreviewDto).collect(Collectors.toList());

        return FollowsResponseDto.FollowerPreviewListDto.builder()
                .isLast(followsList.isLast())
                .isFirst(followsList.isFirst())
                .totalElements(followsList.getTotalElements())
                .totalPage(followsList.getTotalPages())
                .listSize(followsPreviewDtoList.size())
                .followsList(followsPreviewDtoList)
                .build();
    }
}
