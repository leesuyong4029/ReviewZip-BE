package com.example.ReviewZIP.domain.oauth;

import com.example.ReviewZIP.domain.jwt.TokenProvider;
import com.example.ReviewZIP.domain.oauth.dto.request.OauthRequestDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class KakaoController {

    private final OauthService oauthService;
    private final TokenProvider tokenProvider;

    // 유저 관련 token 받은 후 해당 정보로 accessToken 발급
    @GetMapping("/kakao/token")
    public ApiResponse<Map<String, Object>> create(@RequestBody OauthRequestDto.kakaoTokenRequest request) throws JsonProcessingException {

        List<String> kakaoUserInfoList = oauthService.getKakaoUserInfo(request);

        return ApiResponse.onSuccess(oauthService.generateAccessToken(kakaoUserInfoList.get(0),kakaoUserInfoList.get(1) , kakaoUserInfoList.get(2)));

    }

    @GetMapping("/token")
    public Long getUserIdByToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");

        String token = header.replace("Barer: ", "");

        Long userId = tokenProvider.getUserId(token);

        return userId;
    }
}
