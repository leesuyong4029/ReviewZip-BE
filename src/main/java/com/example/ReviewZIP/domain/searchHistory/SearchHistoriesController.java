package com.example.ReviewZIP.domain.searchHistory;

import com.example.ReviewZIP.domain.user.UsersService;
import com.example.ReviewZIP.global.response.ApiResponse;
import com.example.ReviewZIP.global.response.code.resultCode.SuccessStatus;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/history")
public class SearchHistoriesController {
    private final SearchHistoriesService searchHistoriesService;
    private final UsersService usersService;

    @PostMapping("/users/{userId}")
    @GetMapping("/search/name")
    @Operation(summary = "유저 검색 저장하기 API",description = "유저의 id를 받아 해당 유저 검색 기록을 저장")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER408", description = "유저를 찾을 수 없음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "userId", description = "유저의 아이디"),
    })
    public ApiResponse<SuccessStatus> saveUserSearchHistory(@AuthenticationPrincipal UserDetails user, @PathVariable(name = "userId")Long userId){
        searchHistoriesService.saveUserSearchHistory(usersService.getUserId(user), userId);

        return ApiResponse.onSuccess(SuccessStatus._OK);
    }

    @PostMapping("/hashtags")
    @Operation(summary = "해시태그 검색 저장하기 API",description = "해시태그의 값을 받아 저장")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "hashtag", description = "해시태그"),
    })
    public ApiResponse<SuccessStatus> saveHashtagSearchHistory(@AuthenticationPrincipal UserDetails user, @RequestParam(name = "hashtag")String hashtag){
        searchHistoriesService.saveHashtagSearchHistory(usersService.getUserId(user), hashtag);

        return ApiResponse.onSuccess(SuccessStatus._OK);
    }

    @DeleteMapping("/{historyId}")
    @Operation(summary = "검색기록 삭제하기 API",description = "검색기록의 id를 받아 해당 객체를 삭제")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "HISTORY401", description = "해당 검색기록을 찾을 수 없음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<SuccessStatus> deleteHistory(@AuthenticationPrincipal UserDetails user,@PathVariable(name = "historyId")Long historyId){
        searchHistoriesService.deleteUserSearchHistory(usersService.getUserId(user), historyId);

        return ApiResponse.onSuccess(SuccessStatus._OK);
    }


}
