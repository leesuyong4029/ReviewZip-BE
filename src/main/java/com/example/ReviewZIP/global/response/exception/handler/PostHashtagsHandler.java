package com.example.ReviewZIP.global.response.exception.handler;

import com.example.ReviewZIP.global.response.code.BaseErrorCode;
import com.example.ReviewZIP.global.response.exception.GeneralException;

public class PostHashtagsHandler extends GeneralException {
    public PostHashtagsHandler(BaseErrorCode errorCode) {super(errorCode);}
}

