package com.example.ReviewZIP.domain.token.dto.response;

import com.example.ReviewZIP.domain.user.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpResponseDto {
    private String nickname;

    public static SignUpResponseDto signUpResponseDto(Users user) {
        return SignUpResponseDto.builder()
                .nickname(user.getNickname())
                .build();
    }
}
