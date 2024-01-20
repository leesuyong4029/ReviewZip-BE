package com.example.ReviewZIP.domain.postLike;



import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersConverter;
import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostLikesController {

    private final PostLikesService postLikesService;


    @GetMapping("/{postId}/users")
    public ApiResponse<UserResponseDto.UserPreviewListDto> getUsersByPostIdFromPostLikes(@PathVariable Long postId, @RequestParam (defaultValue = "0") Integer page) {
        Page<Users> pageUsers = postLikesService.getUsersByPostId(postId, page);
        UserResponseDto.UserPreviewListDto userListDto = UsersConverter.toUserListDto(pageUsers);
        return ApiResponse.onSuccess(userListDto);
    }

}
