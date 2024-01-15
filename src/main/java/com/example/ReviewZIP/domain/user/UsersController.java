package com.example.ReviewZIP.domain.user;


import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.user.dto.response.OtherPostsResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UsersController {
    private final UsersService usersService;

    @GetMapping("/{userId}/posts")
    public ResponseEntity<OtherPostsResDto.PostPreviewListDto> getOtherPostList(@PathVariable(name = "userId") Long userId
            , @RequestParam(name = "size") Integer size ,@RequestParam(name = "page") Integer page){
        Page<Posts> UserPage = usersService.getOtherPostList(userId, size, page);

        return ResponseEntity.ok(UsersConverter.postPreviewListDto(UserPage));
    }
}
