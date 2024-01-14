package com.example.ReviewZIP.domain.follow;

import com.example.ReviewZIP.domain.follow.dto.response.FollowResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/follows")
public class FollowsController {
    private final FollowsService followsService;

    @PostMapping("/users/{userId}")
    public ResponseEntity<FollowResDto> follow(@PathVariable(name="userId") Long userId){
        Follows follows = followsService.createFollowing(userId);

        return ResponseEntity.ok(FollowsConverter.toCreateFollowDto(follows));
    }
}
