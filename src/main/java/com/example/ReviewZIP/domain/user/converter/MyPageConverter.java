package com.example.ReviewZIP.domain.user.converter;

import com.example.ReviewZIP.domain.user.dto.response.MyPageResDto;
import com.example.ReviewZIP.domain.user.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class MyPageConverter {
    public MyPageResDto convert(Users user) {
        return new MyPageResDto(
                user.getId(),
                user.getName(),
                user.getNickname(),
                user.getProfile_image()
        );
    }
}
