package com.example.ReviewZIP.global.sms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class SmsDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class PasswordResetRequestDto {
        private String name;
        private String phoneNum;
        private String certificationNum;
        private String password;
    }
}
