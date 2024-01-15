package com.example.ReviewZIP.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

// Enum Naming Format : {행위}_{목적어}_{성공여부}
// Message Format : 동사 명사형으로 마무리
@Getter
@AllArgsConstructor
public enum ResultCode {
    // OAuth
    OAUTH_USER_CREATE_SUCCESS("0U001", "소셜 로그인 사용자 생성 성공"),


    // Users
    USER_CREATE_SUCCESS("U001", "사용자 생성 성공"),
    USER_LOGIN_SUCCESS("U002", "사용자 로그인 성공"),
    USER_AUTHENTICATION_MESSAGE_SEND_SUCCESS("U003", "사용자 인증 번호 전송 성공"),
    USER_TOKEN_REISSUE_SUCCESS("U004", "사용자 토큰 재발급 성공"),
    USER_INFO_GET_SUCCESS("U005", "내 정보(마이페이지) 가져오기 성공"),
    USER_NICKNAME_CHANGE_SUCCESS("U006", "닉네임 수정하기 성공"),
    USER_PROFILE_IMAGE_CHANGE_SUCCESS("U007", "프로필 이미지 수정하기 성공"),
    GET_MY_UPLOAD_COMMENT_SUCCESS("U008", "내가 업로드한 게시글 가져오기 성공"),
    GET_MY_SCRAB_COMMENT_SUCCESS("U009", "내가 스크랩한 게시글 가져오기 성공"),
    GET_SPECIFIC_USER_COMMENT_SUCCESS("U010", "특정 유저가 올린 게시글들 가져오기 성공"),
    GET_OTHER_INFO_SUCCESS("U011", "다른 사람 정보 가져오기 성공"),
    USER_DELETE_SUCCESS("U012", "유저 삭제 성공"),
    GET_MY_FOLLOWER_LIST_SUCCESS("U013", "내 팔로워 목록 가져오기 성공"),
    GET_MY_FOLLOWING_LIST_SUCCESS("U014", "내 팔로잉 목록 가져오기 성공"),
    GET_SPECIFIC_USER_FOLLOWER_LIST_SUCCESS("U015", "특정유저의 팔로워 목록 가져오기 성공"),
    GET_SPECIFIC_USER_FOLLOWING_LIST_SUCCESS("U016", "특정유저의 팔로잉 목록 가져오기 성공"),

    // Posts
    POST_S3_IMAGE_UPLOAD_SUCCESS("P001", "S3 이미지 업로드 성공"),
    POST_CREATE_SUCCESS("P002", "게시글 생성 성공"),
    POST_CHANGE_SUCCESS("P003", "게시글 수정 성공"),
    GET_OTHER_USERS_COMMENT_SUCCESS("P004", "특정 유저 게시글 가져오기 성공"),
    GET_RANDOM_COMMENT_SUCCESS("P005", "랜덤으로 게시글들 가져오기 성공"),
    POST_COMMENT_DELETE_SUCCESS("P006", "게시글 삭제 성공"),
    GET_LIKE_PEOPLE_LIST_SUCCESS("P007", "공감 누른 사람들 목록 가져오기 성공"),
    POST_CHOOSE_LIKE_SUCCESS("P008", "공감 누르기 성공"),
    POST_CANCEL_LIKE_SUCCESS("P009", "공감 해제하기 성공"),

    // Searchs
    SEARCH_HASHTAG_SUCCESS("S001", "해쉬태그로 검색 성공"),
    SEARCH_STORE_NAME_SUCCESS("S002", "가게 이름으로 검색 성공"),
    SEARCH_USER_NICKNAME_SUCCESS("S003", "유저 닉네임으로 검색 성공"),
    SEARCH_USER_NAME_SUCCESS("S004", "유저 이름으로 검색 성공"),
    SEARCH_STORE_LOCATION_SUCCESS("S005", "가게 위치로 검색 성공"),

    // Follows
    FOLLOW_USER_BY_USER_ID_SUCCESS("F001", "유저 아이디로 유저 팔로우 성공"),
    UNFOLLOW_USER_BY_USER_ID_SUCCESS("F002", "유저 아이디로 유저 언팔로우 성공");


    private final String code;
    private final String message;
}
