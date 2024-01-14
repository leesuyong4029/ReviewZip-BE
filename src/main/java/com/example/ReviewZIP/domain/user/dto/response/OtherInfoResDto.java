package com.example.ReviewZIP.domain.user.dto.response;

import com.example.ReviewZIP.domain.image.Images;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtherInfoResDto {
    private Long userId;
    private String name;
    private String nickname;
    private String imageUrl;
    private Integer followingNum;
    private Integer followerNum;
}
