package com.example.ReviewZIP.domain.oauth.dto.request;

import lombok.Getter;


public class OauthRequestDto {

    @Getter
    public static class kakaoTokenRequestDto{
        String token;
    }
}
