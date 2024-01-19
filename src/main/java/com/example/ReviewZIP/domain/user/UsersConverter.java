package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.Follows;
import com.example.ReviewZIP.domain.user.dto.response.FollowResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UsersConverter {
    public static FollowResponseDto.FollowerPreviewDto toFollowerPreviewDto(Follows follows){
        return FollowResponseDto.FollowerPreviewDto.builder()
                .followerId(follows.getSender().getId())
                .nickname(follows.getSender().getNickname())
                .profileUrl(follows.getReceiver().getProfileUrl())
                .build();
    }

    public static FollowResponseDto.FollowerPreviewListDto toFollowsPreviewListDto(Page<Follows> followsList){
        List<FollowResponseDto.FollowerPreviewDto> followsPreviewDtoList = followsList.stream()
                .map(UsersConverter::toFollowerPreviewDto).collect(Collectors.toList());

        return FollowResponseDto.FollowerPreviewListDto.builder()
                .isLast(followsList.isLast())
                .isFirst(followsList.isFirst())
                .totalElements(followsList.getTotalElements())
                .totalPage(followsList.getTotalPages())
                .listSize(followsPreviewDtoList.size())
                .followsList(followsPreviewDtoList)
                .build();
    }
}
