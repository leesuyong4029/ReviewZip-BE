package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.Follows;
import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;
import com.example.ReviewZIP.domain.scrab.Scrabs;
import com.example.ReviewZIP.domain.searchHistory.SearchHistories;
import com.example.ReviewZIP.domain.user.dto.request.UserRequestDto;
import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import com.example.ReviewZIP.domain.userStores.UserStoresService;
import com.example.ReviewZIP.domain.userStores.dto.response.UserStoresResponseDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import com.example.ReviewZIP.global.response.code.resultCode.SuccessStatus;
import com.example.ReviewZIP.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.ReviewZIP.domain.user.UsersConverter.toHistoryDtoList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UsersController {
    private final UsersService usersService;
    private final UserStoresService userStoresService;

    @GetMapping("/{userId}/stores")
    @Operation(summary = "특정 유저의 관심 장소 목록 API",description = "특정 특정 유저의 관심 장소 목록을 가져온다, 반환 시 StoreInfoListDto 사용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "유저가 존재하지 않습니다",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "userId", description = "유저의 아이디"),
    })
    public ApiResponse<UserStoresResponseDto.StoreInfoListDto> getStoresByUser(@PathVariable Long userId) {
        return ApiResponse.onSuccess(userStoresService.getStoreInfo(userId));
    }
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
    @Operation(summary = "나의 팔로잉 목록 가져오기 API",description = "토큰 인증 후 나의 팔로잉 목록 조회, UserPreviewDto 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "토큰에 해당하는 유저 없음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<List<UserResponseDto.UserPreviewDto>> getUserFollowingList(){
        List<Follows> FollowingList = usersService.getFollowingList(1L); //수정 필요
        List<Long> followingIdList = usersService.getFollowigIdList(1L);
        return ApiResponse.onSuccess(UsersConverter.toFollowingPreviewListDto(FollowingList, followingIdList));
    }

    @GetMapping("/me/followers")
    @Operation(summary = "나의 팔로워 목록 가져오기 API",description = "토큰 인증 후 나의 팔로워 목록 조회, UserPreviewDto 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "토큰에 해당하는 유저 없음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<List<UserResponseDto.UserPreviewDto>> getUserFollowerList(){

        List<Follows> FollowerList = usersService.getFollowerList(1L); //수정 필요
        List<Long> followingIdList = usersService.getFollowigIdList(1L);

        return ApiResponse.onSuccess(UsersConverter.toFollowerPreviewListDto(FollowerList, followingIdList));
    }

    @GetMapping("/{userId}/followings")
    @Operation(summary = "특정 유저의 팔로잉 목록 가져오기 API",description = "특정 유저의 id를 이용하여 해당 유저의 팔로잉 목록 조회, UserPreviewDto 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "유저가 존재하지 않습니다",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "userId", description = "유저의 아이디"),
    })
    public ApiResponse<List<UserResponseDto.UserPreviewDto>> getOtherFollowingList(@PathVariable(name = "userId") Long userId){
        List<Follows> FollowingList = usersService.getFollowingList(userId);
        List<Long> followingIdList = usersService.getFollowigIdList(userId);
        return ApiResponse.onSuccess(UsersConverter.toFollowingPreviewListDto(FollowingList, followingIdList));
    }

    @GetMapping("/{userId}/followers")
    @Operation(summary = "특정 유저의 팔로워 목록 가져오기 API",description = "특정 유저의 id를 이용하여 해당 유저의 팔로워 목록 조회, UserPreviewDto 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "유저가 존재하지 않습니다",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "userId", description = "유저의 아이디"),
    })
    public ApiResponse<List<UserResponseDto.UserPreviewDto>> getOtherFollowerList(@PathVariable(name = "userId")Long userId){
        List<Follows> FollowerList = usersService.getFollowerList(userId);
        List<Long> followingIdList = usersService.getFollowigIdList(userId);
        return ApiResponse.onSuccess(UsersConverter.toFollowerPreviewListDto(FollowerList, followingIdList));
    }

    @GetMapping("/me/posts")
    @Operation(summary = "나의 게시물 목록 가져오기 API",description = "토큰 인증 후 게시글 목록 가져오기, UserInfoDto & ImageDto & HashtagDto & PostInfoDto 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<List<PostResponseDto.PostInfoDto>> getUserPostList(){
        // 토큰값 받아서 유저확인 하는 부분, 일단 1L로 대체
        List<Posts> postList = usersService.getPostList(1L);
        List<PostResponseDto.PostInfoDto> postInfoDtoList = usersService.getPostInfoDtoList(1L, postList);
        return ApiResponse.onSuccess(postInfoDtoList);
    }

    @GetMapping("/me/posts/scrabs")
    @Operation(summary = "내가 스크랩한 게시물 가져오기 API",description = "토큰 인증 후 스크랩한 게시글들의 목록(대표 이미지들)을 반환, UserInfoDto & ImageDto & HashtagDto & PostInfoDto 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "토큰에 해당하는 유저 없음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<List<PostResponseDto.PostInfoDto>> getUserScrabList() {
        List<Scrabs> scrabList = usersService.getScrabList(1L);
        List<PostResponseDto.PostInfoDto> scrabInfoDtoList = usersService.getScrabInfoDtoList(1L, scrabList);
        return ApiResponse.onSuccess(scrabInfoDtoList);
    }

    @GetMapping("/{userId}/posts")
    @Operation(summary = "특정 유저의 게시글 목록 가져오기 API",description = "특정 유저의 id를 받아 게시글들의 목록(대표 이미지들)을 반환, UserInfoDto & ImageDto & HashtagDto & PostInfoDto 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "토큰에 해당하는 유저 없음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "userId", description = "유저의 아이디"),
    })
    public ApiResponse<List<PostResponseDto.PostInfoDto>> getOtherPostList(@PathVariable(name = "userId") Long userId){
        List<Posts> postList = usersService.getPostList(userId);
        List<PostResponseDto.PostInfoDto> postInfoDtoList = usersService.getPostInfoDtoList(userId, postList);
        return ApiResponse.onSuccess(postInfoDtoList);
    }

     // 특정 유저의 스크랩들 가져오기
     @GetMapping("/{userId}/posts/scrabs")
     @Operation(summary = "특정 유저가 스크랩한 게시물 가져오기 API",description = "특정 유저의 id를 받아 스크랩한 게시글들의 목록(대표 이미지들)을 반환, UserInfoDto & ImageDto & HashtagDto & PostInfoDto 이용")
     @ApiResponses({
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "유저가 존재하지 않습니다",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
     })
     @Parameters({
             @Parameter(name = "userId", description = "유저의 아이디"),
     })
     public ApiResponse<List<PostResponseDto.PostInfoDto>> getOtherScrabList(@PathVariable(name = "userId") Long userId) {
         List<Scrabs> scrabList = usersService.getScrabList(userId);
         List<PostResponseDto.PostInfoDto> scrabInfoDtoList = usersService.getScrabInfoDtoList(userId, scrabList);
         return ApiResponse.onSuccess(scrabInfoDtoList);
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
    public ApiResponse<UserResponseDto.OtherUserInfoDto> getOtherInfo(@PathVariable(name = "userId") Long userId){

        return ApiResponse.onSuccess(usersService.getOtherInfo(userId));
     }

    @GetMapping("/me")
    @Operation(summary = "나의 정보(프로필) API 가져오기",description = " 나의 정보를 가져오기, UserInfoDto 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })

    public ApiResponse<UserResponseDto.UserInfoDto> getUserInfo(@AuthenticationPrincipal UserDetails user){

        return ApiResponse.onSuccess(usersService.getUserInfo(user.getUsername()));
    }

    @PatchMapping("/me/profileUrl")
    @Operation(summary = "프로필 이미지 수정하기 API", description = "프로필 이미지 수정하기, UserProfileDto 사용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "유저가 존재하지 않습니다",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<UserRequestDto.UserProfileUrlDto> updateProfileUrl(@RequestBody UserRequestDto.UserProfileUrlDto userProfileUrlDto){

        UserRequestDto.UserProfileUrlDto ProfileUrlDto = usersService.updateProfileUrl(1L, userProfileUrlDto);
        return ApiResponse.onSuccess(ProfileUrlDto);
    }

    @PatchMapping("/me/nickname")
    @Operation(summary = "닉네임 수정하기 API", description = "마이페이지 닉네임 수정하기, UserNicknameDto 사용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "유저가 존재하지 않습니다", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<UserRequestDto.UserNicknameDto> updateUserNickname(@RequestBody UserRequestDto.UserNicknameDto userNicknameDto) {

        UserRequestDto.UserNicknameDto NicknameDto = usersService.updateUserNickname(1L, userNicknameDto);
        return ApiResponse.onSuccess(NicknameDto);
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

    @GetMapping("/me/histories")
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
