package com.example.ReviewZIP.domain.user;


import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UsersController {
    private final UsersService usersService;


    @GetMapping("/{userId}/posts")
    @ApiOperation(value = "특정 유저의 게시글 가져오기")
    public ApiResponse<UserResponseDto.PostPreviewListDto> getOtherPostList(@PathVariable(name = "userId") Long userId
            , @RequestParam(name = "size") Integer size , @RequestParam(name = "page") Integer page){
        Page<Posts> UserPage = usersService.getOtherPostList(userId, size, page);

        return ApiResponse.onSuccess(UsersConverter.postPreviewListDto(UserPage));
    }
}
