package com.example.ReviewZIP.domain.sms;

import com.example.ReviewZIP.domain.sms.dto.request.SmsDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import com.example.ReviewZIP.global.response.code.resultCode.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "sms-controller", description = "SMS 인증번호 전송, 인증, 비밀번호 변경 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/sms-certification")
public class SmsController {
    private final SmsService smsService;

    @PostMapping("/send")
    @Operation(summary = "SMS 인증번호 전송 API", description = "SMS 인증번호 전송하기, PasswordResetRequestDto 사용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER412", description = "해당 전화번호가 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<SuccessStatus> sendSMS(@RequestBody SmsDto.PasswordResetRequestDto requestDto){

        smsService.sendSms(requestDto);
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }

    // 인증번호 확인
    @PostMapping("/confirm")
    @Operation(summary = "SMS 인증번호 확인 API",  description = "SMS 인증번호 확인하기, PasswordResetRequestDto 사용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SMS401", description = "인증번호가 일치하지 않습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<SuccessStatus> verifySms(@RequestBody SmsDto.PasswordResetRequestDto requestDto) {
        smsService.verifySms(requestDto);
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }

    @PatchMapping("/reset-password")
    @Operation(summary = "비밀번호 재설정 API",  description = "SMS 인증번호 확인 후 비밀번호 재설정하기, PasswordResetRequestDto 사용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SMS401", description = "인증번호가 일치하지 않습니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USERS414", description = "존재하지 않는 계정입니다.", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<SuccessStatus> resetPassword(@RequestBody SmsDto.PasswordResetRequestDto requestDto) {
        smsService.resetPassword(requestDto);
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }
}
