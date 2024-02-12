package com.example.ReviewZIP.global.response.exception.handler;

import com.example.ReviewZIP.global.response.code.BaseErrorCode;
import com.example.ReviewZIP.global.response.exception.GeneralException;

public class UsersHandler extends GeneralException {
    public UsersHandler(BaseErrorCode errorCode) {super(errorCode);}

}
