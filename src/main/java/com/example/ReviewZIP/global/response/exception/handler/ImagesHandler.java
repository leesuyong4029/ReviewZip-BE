package com.example.ReviewZIP.global.response.exception.handler;

import com.example.ReviewZIP.global.response.code.BaseErrorCode;
import com.example.ReviewZIP.global.response.exception.GeneralException;

public class ImagesHandler extends GeneralException {
    public ImagesHandler(BaseErrorCode errorCode) {super(errorCode);};
}
