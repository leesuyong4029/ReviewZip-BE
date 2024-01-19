package com.example.ReviewZIP.domain.follow;

import com.example.ReviewZIP.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/follows")
public class FollowsController {
    private final FollowsService followsService;
    @DeleteMapping("/users/{userId}")
    public ApiResponse<Void> unfollowUser(@PathVariable(name="userId")Long userId){
        followsService.unfollowUser(userId);

        return ApiResponse.onSuccess(null);
    }
}
