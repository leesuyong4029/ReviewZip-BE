package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.Follows;
import com.example.ReviewZIP.domain.user.dto.response.FollowsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UsersController {
    private final UsersService usersService;

    // 특정 유저의 팔로워 목록 가져오기
    @GetMapping("/{userId}/followers")
    public ResponseEntity<FollowsResponseDto.FollowsPreviewListDto> getOtherFollowerList(@PathVariable(name = "userId")Long userId, @RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size){
        Page<Follows> FollowsPage = usersService.getOtherFollowerList(userId, page, size);

        return ResponseEntity.ok(UsersConverter.followsPreviewListDto(FollowsPage));
    }

}
