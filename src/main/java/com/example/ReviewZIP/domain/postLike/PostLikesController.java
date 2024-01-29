package com.example.ReviewZIP.domain.postLike;



import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersConverter;
import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostLikesController {

    private final PostLikesService postLikesService;

    @GetMapping("/{postId}/users")
    @Operation(summary = "게시물에 공감을 누른 유저리스트를 검색하는 API",description = "게시물 아이디를 조회하여 게시물에 공감을 누른 유저 리스트를 검색, 반환 시 UserPreviewListDto 사용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POSTLIKE403", description = "포스트에 공감을 한 사람이 없음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "postId", description = "게시물 아이디"),
            @Parameter(name = "page", description = "페이지 번호"),
            @Parameter(name = "size", description = "페이징 사이즈")
    })
    public ApiResponse<UserResponseDto.UserPreviewListDto> getPostLikeUserList(@PathVariable Long postId, @RequestParam (defaultValue = "0") Integer page, @RequestParam (defaultValue = "0") Integer size) {
        Page<Users> pageUsers = postLikesService.getUsersByPostId(postId, page, size);
        UserResponseDto.UserPreviewListDto userListDto = UsersConverter.toUserListDto(pageUsers);
        return ApiResponse.onSuccess(userListDto);
    }

}
