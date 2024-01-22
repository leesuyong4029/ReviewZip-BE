package com.example.ReviewZIP.domain.user;


import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.postLike.PostLikesRepository;
import com.example.ReviewZIP.domain.scrab.Scrabs;
import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.PostsHandler;
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
    public ApiResponse<UserResponseDto.PostPreviewListDto> getOtherPostList(@PathVariable(name = "userId") Long userId
            , @RequestParam(name = "page") Integer page , @RequestParam(name = "size") Integer size){
        Page<Posts> UserPage = usersService.getOtherPostList(userId, page, size);

        if(UserPage.isEmpty()){
            throw new PostsHandler(ErrorStatus.POST_NOT_FOUND);
        }

        return ApiResponse.onSuccess(UsersConverter.toPostPreviewListDto(UserPage));

    }

     @GetMapping("/{userId}/posts/scrabs")
    public ApiResponse<UserResponseDto.PostPreviewListDto> getOtherScrabList(@PathVariable(name = "userId") Long userId, @RequestParam(name = "page")Integer page, @RequestParam(name = "size") Integer size){
        Page<Scrabs> UserPage = usersService.getOtherScrabList(userId,page, size);

        if(UserPage.isEmpty()){
            throw new PostsHandler(ErrorStatus.SCRAB_LIST_NOT_FOUND);
        }
        return ApiResponse.onSuccess(UsersConverter.toScrabPreviewListDto(UserPage));
    }
}
