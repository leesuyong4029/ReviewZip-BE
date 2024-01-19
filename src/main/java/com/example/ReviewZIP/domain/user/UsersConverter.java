package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.Follows;
import com.example.ReviewZIP.domain.user.dto.response.FollowResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UsersConverter {
    public static FollowResponseDto.FollowingPreviewDto toFollowingPreviewDto(Follows follows){
        return FollowResponseDto.FollowingPreviewDto.builder()
                .followingId(follows.getReceiver().getId())
                .profileUrl(follows.getReceiver().getProfileUrl())
                .nickname(follows.getReceiver().getNickname())
                .build();
    }

    public static FollowResponseDto.FollowingPreviewListDto toFollowingPreviewListDto(Page<Follows> followsList){
        List<FollowResponseDto.FollowingPreviewDto> followingPreviewDtoList = followsList.stream()
                .map(UsersConverter::toFollowingPreviewDto).collect(Collectors.toList());

        return FollowResponseDto.FollowingPreviewListDto.builder()
                .isLast(followsList.isLast())
                .isFirst(followsList.isFirst())
                .totalElements(followsList.getTotalElements())
                .totalPage(followsList.getTotalPages())
                .listSize(followingPreviewDtoList.size())
                .followsList(followingPreviewDtoList)
                .build();
    }
}
