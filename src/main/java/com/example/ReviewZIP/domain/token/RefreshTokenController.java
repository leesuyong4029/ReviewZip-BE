package com.example.ReviewZIP.domain.token;

import com.example.ReviewZIP.domain.token.dto.request.KakaoRequestDto;
import com.example.ReviewZIP.domain.token.dto.request.LoginRequestDto;
import com.example.ReviewZIP.domain.token.dto.request.SignUpRequestDto;
import com.example.ReviewZIP.domain.token.dto.response.TokenDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import com.example.ReviewZIP.global.response.code.resultCode.SuccessStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "token-controller", description = "로컬 로그인, 회원가입 API")
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class RefreshTokenController {
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/local/sign-up")
    @Operation(summary = "로컬 회원가입", description = "JWT 이용해 로컬 회원가입 진행, SignUpRequestDto, SignUpResponseDto 사용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER401", description = "이미 존재하는 이메일입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ApiResponse<SuccessStatus> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        refreshTokenService.signUp(signUpRequestDto);
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }

    @PostMapping("/local/login")
    @Operation(summary = "로컬 로그인", description = "JWT 이용해 로컬 로그인 진행")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER403", description = "비밀번호가 잘못되었습니다.")
    })
    public ApiResponse<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ApiResponse.onSuccess(refreshTokenService.login(loginRequestDto));
    }

    @PostMapping("/kakao/login")
    @Operation(summary = "카카오 로그인", description = "카카오유저 정보가 담긴 token을 KakaoRequestDto로 받아 유저 확인 후 TokenDto반환")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<TokenDto> kakaoLogin(@RequestBody KakaoRequestDto request) throws JsonProcessingException {
        List<String> kakaoUserInfo = refreshTokenService.getKakaoUserInfo(request);
        return ApiResponse.onSuccess(refreshTokenService.kakaoLogin(kakaoUserInfo));
    }
}