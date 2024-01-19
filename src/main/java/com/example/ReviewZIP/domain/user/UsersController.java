package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.PostsHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UsersController {

    private final UsersService usersService;

    @GetMapping("/me/posts")
    public ApiResponse<UserResponseDto.PostPreviewListDto> getUserPostList(@RequestParam(name = "page")Integer page, @RequestParam(name = "size")Integer size){
        Page<Posts> UserPage = usersService.getUserPostList(page, size);

        if(UserPage.isEmpty()){
            throw new PostsHandler(ErrorStatus.POST_LIST_NOT_FOUND);
        }
        return ApiResponse.onSuccess(UsersConverter.toPostPreviewListDto(UserPage));
    }
}
