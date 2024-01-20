package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UsersConverter {
    public static UserResponseDto.UserPreviewDto toUserPreviewDto(Users user) {
        return UserResponseDto.UserPreviewDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .profileImage(user.getProfileUrl())
                .build();
    }

    public static UserResponseDto.UserPreviewListDto toUserListDto(Page<Users> userList) {
        List<UserResponseDto.UserPreviewDto> userDtoList = userList.stream()
                .map(UsersConverter::toUserPreviewDto)
                .collect(Collectors.toList());

        return UserResponseDto.UserPreviewListDto.builder()
                .isLast(userList.isLast())
                .isFirst(userList.isFirst())
                .totalPage(userList.getTotalPages())
                .totalElements(userList.getTotalElements())
                .listSize(userDtoList.size())
                .userList(userDtoList)
                .build();
    }
}