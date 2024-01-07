package com.example.ReviewZIP.domain.user.converter;

import com.example.ReviewZIP.domain.user.dto.response.GetMyPageResDto;
import com.example.ReviewZIP.domain.user.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class MyPageConverter {
    public GetMyPageResDto convert(Users user) {
        return new GetMyPageResDto(
                user.getId(),
                user.getName(),
                user.getNickname(),
                user.getProfile_image()
        );
    }
}
