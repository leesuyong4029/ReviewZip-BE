package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.user.dto.response.UserResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UsersConverter {
    public static UserResponseDTO.UserDTO toUserDTO(Users user) {
        return UserResponseDTO.UserDTO.builder()
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

    public static UserResponseDTO.UserListDTO toUserListDTO(Page<Users> userList) {
        List<UserResponseDTO.UserDTO> userDTOList = userList.stream()
                .map(UsersConverter::toUserDTO)
                .collect(Collectors.toList());

        return UserResponseDTO.UserListDTO.builder()
                .isLast(userList.isLast())
                .isFirst(userList.isFirst())
                .totalPage(userList.getTotalPages())
                .totalElements(userList.getTotalElements())
                .listSize(userDTOList.size())
                .userList(userDTOList)
                .build();
    }
}
