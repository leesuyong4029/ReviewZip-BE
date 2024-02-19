package com.example.ReviewZIP.domain.token;

import com.example.ReviewZIP.domain.token.dto.response.RegenerateTokenResponseDto;

public class RefreshTokenConverter {
    public static RegenerateTokenResponseDto toRegenerateTokenDto(String token){
        return RegenerateTokenResponseDto.builder()
                .accessToken(token)
                .build();
    }
}
