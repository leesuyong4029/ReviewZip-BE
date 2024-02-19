package com.example.ReviewZIP.global.response.exception.handler;

import com.example.ReviewZIP.global.response.code.BaseErrorCode;
import com.example.ReviewZIP.global.response.exception.GeneralException;

public class SmsHandler extends GeneralException {
    public SmsHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
