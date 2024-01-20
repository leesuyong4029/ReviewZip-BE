package com.example.ReviewZIP.domain.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostStatus {
    ENABLED,    // 활성화(default)
    DISABLED,   // 비활성화 ( 장기 미접속자 )
    DELETED;    // 삭제
}
