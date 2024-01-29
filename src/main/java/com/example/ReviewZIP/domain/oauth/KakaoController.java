package com.example.ReviewZIP.domain.oauth;

import com.example.ReviewZIP.domain.jwt.TokenProvider;
import com.example.ReviewZIP.domain.oauth.dto.request.OauthRequestDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/oauth")
public class KakaoController {

    private final OauthService oauthService;

    // 유저 관련 token 받은 후 해당 정보로 accessToken 발급
    @PostMapping("/kakao")
    public ApiResponse<Map<String, Object>> sendAccessToken(@RequestBody OauthRequestDto.kakaoTokenRequest request) throws JsonProcessingException {

        List<String> kakaoUserInfoList = oauthService.getKakaoUserInfo(request);

        return ApiResponse.onSuccess(oauthService.generateAccessToken(kakaoUserInfoList.get(0),kakaoUserInfoList.get(1) , kakaoUserInfoList.get(2)));

    }
}
