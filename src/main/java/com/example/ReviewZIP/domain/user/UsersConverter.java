package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.user.dto.response.DeleteUserResDto;

public class UsersConverter {
    public static DeleteUserResDto toDeleteUserDto(Users user){
        return DeleteUserResDto.builder()
                .userId(user.getId())
                .build();
    }
}
