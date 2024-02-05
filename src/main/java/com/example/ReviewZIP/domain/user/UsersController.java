package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.Follows;
import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.scrab.Scrabs;
import com.example.ReviewZIP.domain.searchHistory.SearchHistories;
import com.example.ReviewZIP.domain.user.dto.response.FollowResponseDto;
import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import com.example.ReviewZIP.global.response.code.resultCode.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.ReviewZIP.domain.user.UsersConverter.toHistoryDtoList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UsersController {
    private final UsersService usersService;

    @GetMapping("/search/name")
    @Operation(summary = "이름으로 유저 검색 API",description = "유저의 이름으로 특정 유저를 검색 (자신이 팔로잉한 대상은 제외), UserPreviewDto 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER408", description = "유저를 찾을 수 없음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "name", description = "유저의 이름"),
    })
    public ApiResponse<List<UserResponseDto.UserPreviewDto>> searchUsersByName(@RequestParam String name) {
        List<Users> userPage = usersService.findUsersByName(name);
        List<Long> followingIdList = usersService.getFollowigIdList(1L);
        List<UserResponseDto.UserPreviewDto> userListDto = UsersConverter.toUserPreviewListDto(userPage, followingIdList);
        return ApiResponse.onSuccess(userListDto);
    }

    @GetMapping("/search/nickname")
    @Operation(summary = "닉네임으로 유저 검색 API",description = "유저의 닉네임으로 특정 유저를 검색 (자신이 팔로잉한 대상은 제외), UserPreviewDto 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER408", description = "유저를 찾을 수 없음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "nickname", description = "유저의 닉네임"),
    })
    public ApiResponse<List<UserResponseDto.UserPreviewDto>> searchUsersByNickname(@RequestParam String nickname) {
        List<Users> userPage = usersService.findUsersByNickname(nickname);
        // 유저는 1L로 설정
        List<Long> followingIdList = usersService.getFollowigIdList(1L);
        List<UserResponseDto.UserPreviewDto> userListDto = UsersConverter.toUserPreviewListDto(userPage, followingIdList);
        return ApiResponse.onSuccess(userListDto);
    }

    @GetMapping("/me/followings")
    @Operation(summary = "나의 팔로잉 목록 가져오기 API",description = "토큰 인증 후 나의 팔로잉 목록 조회, FollowingPreviewDto 이용, 임시로 user id 1의 팔로잉 목록 반환")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "토큰에 해당하는 유저 없음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<List<FollowResponseDto.FollowingPreviewDto>> getUserFollowingList(){
        List<Follows> FollowsPage = usersService.getFollowingList(1L); //수정 필요

        return ApiResponse.onSuccess(UsersConverter.toFollowingPreviewListDto(FollowsPage));
    }

    @GetMapping("/me/followers")
    @Operation(summary = "나의 팔로워 목록 가져오기 API",description = "토큰 인증 후 나의 팔로워 목록 조회, FollowerPreviewDto 이용, 임시로 user id 1의 팔로잉 목록 반환")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "토큰에 해당하는 유저 없음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<List<FollowResponseDto.FollowerPreviewDto>> getUserFollowerList(){

        List<Follows> FollowsPage = usersService.getFollowerList(1L); //수정 필요

        return ApiResponse.onSuccess(UsersConverter.toFollowerPreviewListDto(FollowsPage));
    }

    @GetMapping("/{userId}/followings")
    @Operation(summary = "특정 유저의 팔로잉 목록 가져오기 API",description = "특정 유저의 id를 이용하여 해당 유저의 팔로잉 목록 조회, FollowingPreviewDto 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "유저가 존재하지 않습니다",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "userId", description = "유저의 아이디"),
    })
    public ApiResponse<List<FollowResponseDto.FollowingPreviewDto>> getOtherFollowingList(@PathVariable(name = "userId") Long userId){
        List<Follows> FollowsPage = usersService.getFollowingList(userId);

        return ApiResponse.onSuccess(UsersConverter.toFollowingPreviewListDto(FollowsPage));
    }

    @GetMapping("/{userId}/followers")
    @Operation(summary = "특정 유저의 팔로워 목록 가져오기 API",description = "특정 유저의 id를 이용하여 해당 유저의 팔로워 목록 조회, FollowerPreviewDto 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "유저가 존재하지 않습니다",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "userId", description = "유저의 아이디"),
    })
    public ApiResponse<List<FollowResponseDto.FollowerPreviewDto>> getOtherFollowerList(@PathVariable(name = "userId")Long userId){
        List<Follows> FollowsPage = usersService.getFollowerList(userId);

        return ApiResponse.onSuccess(UsersConverter.toFollowerPreviewListDto(FollowsPage));
    }

    @GetMapping("/me/posts")
    @Operation(summary = "나의 게시물 목록 가져오기 API",description = "토큰 인증 후 게시글 목록 가져오기, PostPreviewDto와 PostPreviewListDto 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호"),
            @Parameter(name = "size", description = "페이징 사이즈")
    })
    public ApiResponse<UserResponseDto.PostPreviewListDto> getUserPostList(@RequestParam(name = "page")Integer page, @RequestParam(name = "size")Integer size){
        // 토큰값 받아서 유저확인 하는 부분, 일단 1L로 대체
        Page<Posts> UserPage = usersService.getPostList(1L, page, size);

        return ApiResponse.onSuccess(UsersConverter.toPostPreviewListDto(UserPage));
    }

    @GetMapping("/me/posts/scrabs")
    @Operation(summary = "내가 스크랩한 게시물 가져오기 API",description = "토큰 인증 후 스크랩한 게시글들의 목록(대표 이미지들)을 반환, PostPreviewDto와 PostPreviewListDto 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "토큰에 해당하는 유저 없음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호"),
            @Parameter(name = "size", description = "페이징 사이즈")
    })
    public ApiResponse<UserResponseDto.PostPreviewListDto> getUserScrabList(@RequestParam(name = "page")Integer page, @RequestParam(name = "size") Integer size) {
        Page<Scrabs> UserPage = usersService.getScrabList(1L, page, size);

        return ApiResponse.onSuccess(UsersConverter.toScrabPreviewListDto(UserPage));
    }

    @GetMapping("/{userId}/posts")
    @Operation(summary = "특정 유저의 게시글 목록 가져오기 API",description = "특정 유저의 id를 받아 게시글들의 목록(대표 이미지들)을 반환, PostPreviewDto와 PostPreviewListDto 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "토큰에 해당하는 유저 없음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "userId", description = "유저의 아이디"),
            @Parameter(name = "page", description = "페이지 번호"),
            @Parameter(name = "size", description = "페이징 사이즈")
    })
    public ApiResponse<UserResponseDto.PostPreviewListDto> getOtherPostList(@PathVariable(name = "userId") Long userId
            , @RequestParam(name = "page") Integer page , @RequestParam(name = "size") Integer size){
        Page<Posts> UserPage = usersService.getPostList(userId, page, size);

        return ApiResponse.onSuccess(UsersConverter.toPostPreviewListDto(UserPage));

    }

     // 특정 유저의 스크랩들 가져오기
     @GetMapping("/{userId}/posts/scrabs")
     @Operation(summary = "특정 유저가 스크랩한 게시물 가져오기 API",description = "특정 유저의 id를 받아 스크랩한 게시글들의 목록(대표 이미지들)을 반환, PostPreviewDto와 PostPreviewListDto 이용")
     @ApiResponses({
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "유저가 존재하지 않습니다",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
     })
     @Parameters({
             @Parameter(name = "userId", description = "유저의 아이디"),
             @Parameter(name = "page", description = "페이지 번호"),
             @Parameter(name = "size", description = "페이징 사이즈")
     })
    public ApiResponse<UserResponseDto.PostPreviewListDto> getOtherScrabList(@PathVariable(name = "userId") Long userId, @RequestParam(name = "page")Integer page, @RequestParam(name = "size") Integer size) {
         Page<Scrabs> UserPage = usersService.getScrabList(userId, page, size);

         return ApiResponse.onSuccess(UsersConverter.toScrabPreviewListDto(UserPage));
     }

    @GetMapping("/{userId}")
    @Operation(summary = "특정 유저의 정보(프로필) API 가져오기",description = "user id를 받아 특정 유저의 정보(프로필) 가져오기, UserInfoDto 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "유저가 존재하지 않습니다",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "userId", description = "유저의 아이디"),
    })
    public ApiResponse<UserResponseDto.UserInfoDto> getOtherInfo(@PathVariable(name = "userId") Long userId){

        return ApiResponse.onSuccess(usersService.getOtherInfo(userId));
     }

    @DeleteMapping("/{userId}")
    @Operation(summary = "유저 삭제하기 API",description = "유저를 삭제한다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "userId", description = "유저의 아이디"),
    })
    public ApiResponse<SuccessStatus> deleteUser(@PathVariable(name = "userId")Long userId) {
        usersService.deleteUser(userId);
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }

    @GetMapping("/{userId}/histories")
    @Operation(summary = "검색기록 가져오기 API",description = "나의 검색기록을 가져온다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SEARCH402", description = "유효하지않은 검색 타입입니다.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<List<UserResponseDto.HistoryDto>> getHistory(){
        // 1L로 설정
        List<SearchHistories> historyList = usersService.getHistoryList(1L);
        List<Long> followingIdList = usersService.getFollowigIdList(1L);
        return ApiResponse.onSuccess(toHistoryDtoList(historyList, followingIdList));
    }

}
