package com.example.ReviewZIP.domain.oauth;

import com.example.ReviewZIP.domain.jwt.TokenProvider;
import com.example.ReviewZIP.domain.oauth.dto.request.OauthRequestDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "카카오 소셜 로그인 API",description = "카카오 인가 코드를 받아 access token 발급, KakaoTokenRequest의 token 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "GLOBAL502", description = "유효하지 못한 카카오 인가코드입니다",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<Map<String, Object>> sendAccessToken(@RequestBody OauthRequestDto.kakaoTokenRequestDto request) throws JsonProcessingException {

        List<String> kakaoUserInfoList = oauthService.getKakaoUserInfo(request);

        return ApiResponse.onSuccess(oauthService.generateAccessToken(kakaoUserInfoList.get(0),kakaoUserInfoList.get(1) , kakaoUserInfoList.get(2)));

    }
}
