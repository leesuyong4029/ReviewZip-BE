package com.example.ReviewZIP.domain.follow;

import com.example.ReviewZIP.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/follows")
public class FollowsController {
    private final FollowsService followsService;

    @PostMapping("/users/{userId}")
    @Operation(summary = "유저 팔로우 하기 API",description = "유저의 id를 받아 해당 유저 팔로우")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "팔로우 할 유저가 존재하지 않습니다",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "userId", description = "팔로우할 유저의 아이디"),
    })
    public ApiResponse<Void> follow(@PathVariable(name="userId") Long userId) {
        Follows follows = followsService.createFollowing(userId);

        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/users/{userId}")
    @Operation(summary = " 유저 언팔로우 하기 API",description = "유저의 id를 받아 해당 유저 언팔로우")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER404", description = "언팔로우 할 유저가 존재하지 않습니다",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "userId", description = "팔로우 취소할 유저의 아이디"),
    })
    public ApiResponse<Void> unfollowUser(@PathVariable(name="userId")Long userId){
        followsService.unfollowUser(userId);

        return ApiResponse.onSuccess(null);
    }
}
