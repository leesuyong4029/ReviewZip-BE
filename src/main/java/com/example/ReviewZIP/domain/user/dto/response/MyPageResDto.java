package com.example.ReviewZIP.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MyPageResDto {
    private Long userId;
    private String name;
    private String nickname;
    private String profileImage;

}
