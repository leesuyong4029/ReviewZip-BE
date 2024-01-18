package com.example.ReviewZIP.global.response.code.resultCode;


import com.example.ReviewZIP.global.response.code.BaseErrorCode;
import com.example.ReviewZIP.global.response.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// Enum Naming Format : {주체}_{이유}
// Message format : 동사 명사형으로 마무리
@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    // Global
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GLOBAL501", "서버 오류"),
    INPUT_INVALID_VALUE(HttpStatus.BAD_REQUEST, "GLOBAL401", "잘못된 입력"),

    // OAuth
    EXPIRED_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, "OAUTH401", "기존 토큰이 만료되었습니다. 토큰을 재발급해주세요."),

    // User
    USER_EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "USER401", "중복된 이메일입니다."),
    USER_EXISTS_NAME(HttpStatus.BAD_REQUEST, "USER402", "중복된 이름입니다."),
    FAILED_TO_PASSWORD(HttpStatus.BAD_REQUEST, "USER403", "비밀번호가 잘못되었습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404", "유저를 찾을 수 없습니다."),
    FOLLOWER_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "USER405", "팔로워 목록을 찾을 수 없습니다."),
    FOLLOWING_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "USER406", "팔로잉 목록을 찾을 수 없습니다."),
    NICKNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "USER407", "존재하지 않는 유저 닉네임입니다."),
    NAME_NOT_FOUND(HttpStatus.NOT_FOUND, "USER408", "존재하지 않는 유저 이름입니다."),
    USER_CREATE_FAIL(HttpStatus.BAD_REQUEST,"USER409", "유저 생성에 실패하였습니다."),


    // Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST401", "게시글을 찾을 수 없습니다."),
    LIKE_PERSON_NOT_FOUND(HttpStatus.BAD_REQUEST, "POST402", "공감 누른 사람을 찾을 수 없습니다."),
    POST_CREATE_FAIL(HttpStatus.BAD_REQUEST,"POST403", "게시글 작성에 실패하였습니다"),

    // Hashtag
    HASHTAG_NOT_FOUND(HttpStatus.NOT_FOUND, "HASHTAG401", "존재하지 않는 해쉬태그입니다."),

    //Store
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE401", "존재하지 않는 가게입니다."),
    LOCATION_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE402", "존재하지 않는 가게 위치입니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
