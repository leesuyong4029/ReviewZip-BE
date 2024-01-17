package com.example.ReviewZIP.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtherInfoResponseDto {
    private Long userId;
    private String name;
    private String nickname;
    private String profileUrl;
    private Integer followingNum;
    private Integer followerNum;
}
