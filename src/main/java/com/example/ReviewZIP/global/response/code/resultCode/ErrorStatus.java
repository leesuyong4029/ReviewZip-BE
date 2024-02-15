package com.example.ReviewZIP.global.response.code.resultCode;


import com.example.ReviewZIP.global.response.code.BaseErrorCode;
import com.example.ReviewZIP.global.response.code.ErrorReasonDto;
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
    KAKAO_TOKEN_ERROR(HttpStatus.BAD_REQUEST, "GLOBAL502", "토큰관련 서버 에러"),
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
    USER_DELETE_FAIL(HttpStatus.BAD_REQUEST,"USER410", "유저 삭제에 실패하였습니다."),
    USER_EXISTS_NICKNAME(HttpStatus.BAD_REQUEST, "USER411", "중복된 닉네임입니다."),


    // Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST401", "게시글을 찾을 수 없습니다."),
    LIKE_PERSON_NOT_FOUND(HttpStatus.BAD_REQUEST, "POST402", "공감 누른 사람을 찾을 수 없습니다."),
    POST_CREATE_FAIL(HttpStatus.BAD_REQUEST,"POST403", "게시글 작성에 실패하였습니다"),
    SCRAB_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST404", "스크랩 목록을 찾을 수 없습니다."),
    NON_USER_POST_REQUIRED(HttpStatus.BAD_REQUEST,"POST405", "사용자가 작성하지 않은 게시글이 필요합니다."),
    POST_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST406", "게시글 목록이 존재하지 않습니다."),

    // Hashtag
    HASHTAG_NOT_FOUND(HttpStatus.NOT_FOUND, "HASHTAG401", "존재하지 않는 해쉬태그입니다."),

    // Store
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE401", "존재하지 않는 가게입니다."),
    LOCATION_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE402", "존재하지 않는 가게 위치입니다."),

    //PostLike
    POSTLIKE_CREATE_FAIL(HttpStatus.NOT_FOUND,"POSTLIKE401","공감 누르기에 실패하였습니다"),
    POSTLIKE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "POSTLIKE402","이미 공감한 게시물입니다"),
    POSTLIKE_NOT_FOUND(HttpStatus.NOT_FOUND,"POSTLIKE403","존재하지 않는 공감입니다."),

    // Image
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "IMAGE401", "파일이 존재하지 않습니다."),
    IMAGE_UPLOAD_FAIL(HttpStatus.BAD_REQUEST, "IMAGE402", "이미지 업로드에 실패하였습니다."),
    IMAGE_NOT_PROVIDED(HttpStatus.NOT_FOUND, "IMAGE403", "프로필 이미지가 존재하지 않습니다."),

    // Follow
    FOLLOW_ALREADY(HttpStatus.BAD_REQUEST, "FOLLOW401", "이미 팔로우한 상태입니다."),

    // SearchHistory
    HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "HISTORY401", "해당하는 검색기록이 존재하지 않습니다."),
    HISTORY_TYPE_NOT_VALID(HttpStatus.BAD_REQUEST, "SEARCH402", "존재하지 않는 타입입니다."),

    // JWT
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH401", "유효하지 않은 ACCESS 토큰입니다."),
    EXPIRED_MEMBER_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH402", "만료된 JWT 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH403", "지원되지 않는 JWT 토큰입니다."),
    ILLEGALARGUMENT_TOKEN(HttpStatus.BAD_REQUEST, "AUTH404", "잘못된 JWT 토큰입니다."),

    // UserStores
    USER_STORES_CREATE_FAIL(HttpStatus.BAD_REQUEST,"USERSTORE401", "유저 관심장소 생성에 실패하였습니다."),
    USER_STORES_DELETE_FAIL(HttpStatus.BAD_REQUEST,"USERSTORE402", "유저 관심장소 삭제에 실패하였습니다."),
    USER_STORES_NOT_FOUND(HttpStatus.NOT_FOUND, "USERSTORE403", "존재하지 않는 유저 관심장소 입니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDto getReason() {
        return ErrorReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDto getReasonHttpStatus() {
        return ErrorReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
