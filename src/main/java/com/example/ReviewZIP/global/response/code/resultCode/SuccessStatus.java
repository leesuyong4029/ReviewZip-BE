package com.example.ReviewZIP.global.response.code.resultCode;

import com.example.ReviewZIP.global.response.code.BaseCode;
import com.example.ReviewZIP.global.response.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// Enum Naming Format : {행위}_{목적어}_{성공여부}
// Message Format : 동사 명사형으로 마무리
@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    _OK(HttpStatus.OK, "COMMON200", "성공입니다."),

    // OAuth
    OAUTH_USER_CREATE_SUCCESS(HttpStatus.OK,"0AUTH001", "소셜 로그인 사용자 생성 성공"),


    // Users
    USER_CREATE_SUCCESS(HttpStatus.OK,"USER201", "사용자 생성 성공"),
    USER_LOGIN_SUCCESS(HttpStatus.OK,"USER202", "사용자 로그인 성공"),
    USER_AUTHENTICATION_MESSAGE_SEND_SUCCESS(HttpStatus.OK,"USER203", "사용자 인증 번호 전송 성공"),
    USER_TOKEN_REISSUE_SUCCESS(HttpStatus.OK,"USER204", "사용자 토큰 재발급 성공"),
    USER_INFO_GET_SUCCESS(HttpStatus.OK,"USER205", "내 정보(마이페이지) 가져오기 성공"),
    USER_NICKNAME_CHANGE_SUCCESS(HttpStatus.OK,"USER206", "닉네임 수정하기 성공"),
    USER_PROFILE_IMAGE_CHANGE_SUCCESS(HttpStatus.OK,"USER207", "프로필 이미지 수정하기 성공"),
    GET_MY_UPLOAD_COMMENT_SUCCESS(HttpStatus.OK,"USER208", "내가 업로드한 게시글 가져오기 성공"),
    GET_MY_SCRAB_COMMENT_SUCCESS(HttpStatus.OK,"USER209", "내가 스크랩한 게시글 가져오기 성공"),
    GET_SPECIFIC_USER_COMMENT_SUCCESS(HttpStatus.OK,"USER210", "특정 유저가 올린 게시글들 가져오기 성공"),
    GET_OTHER_INFO_SUCCESS(HttpStatus.OK,"USER211", "다른 사람 정보 가져오기 성공"),
    USER_DELETE_SUCCESS(HttpStatus.OK,"USER212", "유저 삭제 성공"),
    GET_MY_FOLLOWER_LIST_SUCCESS(HttpStatus.OK,"USER213", "내 팔로워 목록 가져오기 성공"),
    GET_MY_FOLLOWING_LIST_SUCCESS(HttpStatus.OK,"USER214", "내 팔로잉 목록 가져오기 성공"),
    GET_SPECIFIC_USER_FOLLOWER_LIST_SUCCESS(HttpStatus.OK,"USER215", "특정유저의 팔로워 목록 가져오기 성공"),
    GET_SPECIFIC_USER_FOLLOWING_LIST_SUCCESS(HttpStatus.OK,"USER216", "특정유저의 팔로잉 목록 가져오기 성공"),

    // Posts
    POST_S3_IMAGE_UPLOAD_SUCCESS(HttpStatus.OK,"POST201", "S3 이미지 업로드 성공"),
    POST_CREATE_SUCCESS(HttpStatus.OK,"POST202", "게시글 생성 성공"),
    POST_CHANGE_SUCCESS(HttpStatus.OK,"POST203", "게시글 수정 성공"),
    GET_RANDOM_COMMENT_SUCCESS(HttpStatus.OK,"POST205", "랜덤으로 게시글들 가져오기 성공"),
    POST_COMMENT_DELETE_SUCCESS(HttpStatus.OK,"POST206", "게시글 삭제 성공"),
    GET_LIKE_PEOPLE_LIST_SUCCESS(HttpStatus.OK,"POST207", "공감 누른 사람들 목록 가져오기 성공"),
    POST_CHOOSE_LIKE_SUCCESS(HttpStatus.OK,"POST208", "공감 누르기 성공"),
    POST_CANCEL_LIKE_SUCCESS(HttpStatus.OK,"POST209", "공감 해제하기 성공"),

    // Search
    SEARCH_HASHTAG_SUCCESS(HttpStatus.OK,"SEARCH201", "해쉬태그로 검색 성공"),
    SEARCH_STORE_NAME_SUCCESS(HttpStatus.OK,"SEARCH202", "가게 이름으로 검색 성공"),
    SEARCH_USER_NICKNAME_SUCCESS(HttpStatus.OK,"SEARCH203", "유저 닉네임으로 검색 성공"),
    SEARCH_USER_NAME_SUCCESS(HttpStatus.OK,"SEARCH204", "유저 이름으로 검색 성공"),
    SEARCH_STORE_LOCATION_SUCCESS(HttpStatus.OK,"SEARCH205", "가게 위치로 검색 성공"),

    // Follows
    FOLLOW_USER_BY_USER_ID_SUCCESS(HttpStatus.OK,"FOLLOW201", "유저 아이디로 유저 팔로우 성공"),
    UNFOLLOW_USER_BY_USER_ID_SUCCESS(HttpStatus.OK, "FOLLOW402", "유저 아이디로 유저 언팔로우 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build();
    }
}
