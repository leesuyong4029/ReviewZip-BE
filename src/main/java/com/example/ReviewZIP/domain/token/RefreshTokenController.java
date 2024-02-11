package com.example.ReviewZIP.domain.token;

import com.example.ReviewZIP.domain.token.dto.LoginRequestDto;
import com.example.ReviewZIP.domain.token.dto.SignUpRequestDto;
import com.example.ReviewZIP.domain.token.dto.SignUpResponseDto;
import com.example.ReviewZIP.domain.token.dto.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "token-controller", description = "로컬 로그인, 회원가입 API")
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class RefreshTokenController {
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/local/sign-up")
    @Operation(summary = "로컬 회원가입", description = "JWT 이용해 로컬 회원가입 진행")
    @ApiResponses({
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @ApiResponse(responseCode = "USER401", description = "이미 존재하는 이메일입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        SignUpResponseDto savedUserDto = refreshTokenService.signUp(signUpRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedUserDto);
    }

    @PostMapping("/local/login")
    @Operation(summary = "로컬 로그인", description = "JWT 이용해 로컬 로그인 진행")
    @ApiResponses({
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @ApiResponse(responseCode = "USER403", description = "비밀번호가 잘못되었습니다.")
    })
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(refreshTokenService.login(loginRequestDto));
    }
}
