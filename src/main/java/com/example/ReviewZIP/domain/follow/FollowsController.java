package com.example.ReviewZIP.domain.follow;

import com.example.ReviewZIP.domain.follow.dto.response.UnfollowResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public void unfollowUser(@PathVariable(name="userId")Long userId){
        followsService.unfollowUser(userId);
        //추후에 ApiResponse 보낼 예정
    }
}
