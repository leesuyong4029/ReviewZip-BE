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

    // 특정 유저의 팔로잉 목록 가져오기
    @GetMapping("{userId}/following")
    public ResponseEntity<FollowsResponseDto.FollowingPreviewListDto> getOtherFollowingList(@PathVariable(name = "userId") Long userId, @RequestParam(name = "page") Integer page, @RequestParam(name = "size")Integer size){
        Page<Follows> FollowsPage = usersService.getOtherFollowingList(userId, page, size);

        return ResponseEntity.ok(UsersConverter.toFollowingPreviewListDto(FollowsPage));
    }


}
