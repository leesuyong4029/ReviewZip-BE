package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UsersConverter {
    public static UserResponseDto.UserDto toUserDto(Users user) {
        return UserResponseDto.UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .user_id(user.getUser_id())
                .password(user.getPassword())
                .phone_num(user.getPhone_num())
                .nickname(user.getNickname())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static UserResponseDto.UserListDto toUserListDto(Page<Users> userList) {
        List<UserResponseDto.UserDto> userDtoList = userList.stream()
                .map(UsersConverter::toUserDto)
                .collect(Collectors.toList());

        return UserResponseDto.UserListDto.builder()
                .isLast(userList.isLast())
                .isFirst(userList.isFirst())
                .totalPage(userList.getTotalPages())
                .totalElements(userList.getTotalElements())
                .listSize(userDtoList.size())
                .userList(userDtoList)
                .build();
    }
}
