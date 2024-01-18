package com.example.ReviewZIP.global.response.exception.handler;

import com.example.ReviewZIP.global.response.code.BaseErrorCode;
import com.example.ReviewZIP.global.response.exception.GeneralException;

public class PostsHandler extends GeneralException {
    public PostsHandler(BaseErrorCode errorCode) {super(errorCode);}
}
