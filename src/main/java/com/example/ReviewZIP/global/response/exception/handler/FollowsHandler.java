package com.example.ReviewZIP.global.response.exception.handler;

import com.example.ReviewZIP.global.response.code.BaseErrorCode;
import com.example.ReviewZIP.global.response.exception.GeneralException;

public class FollowsHandler extends GeneralException {
    public FollowsHandler(BaseErrorCode errorCode) {super(errorCode);}
}
