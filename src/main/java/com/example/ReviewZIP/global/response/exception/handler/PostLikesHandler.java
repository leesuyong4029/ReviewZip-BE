package com.example.ReviewZIP.global.response.exception.handler;

import com.example.ReviewZIP.global.response.code.BaseErrorCode;
import com.example.ReviewZIP.global.response.exception.GeneralException;

public class PostLikesHandler extends GeneralException{
    public PostLikesHandler(BaseErrorCode errorCode) {super(errorCode);}
}