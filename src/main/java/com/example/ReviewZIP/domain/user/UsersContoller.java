package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.scrab.Scrabs;
import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.PostsHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UsersContoller {

    private final UsersService usersService;

    @GetMapping("/{userId}/posts/scrabs")
    public ApiResponse<UserResponseDto.PostPreviewListDto> getOtherPostList(@PathVariable(name = "userId") Long userId, @RequestParam(name = "page")Integer page, @RequestParam(name = "size") Integer size){
        Page<Scrabs> UserPage = usersService.getOtherScrabList(userId,page, size);

        if(UserPage.isEmpty()){
            throw new PostsHandler(ErrorStatus.SCRAB_LIST_NOT_FOUND);
        }
        return ApiResponse.onSuccess(UsersConverter.toPostPreviewListDto(UserPage));
    }
}
