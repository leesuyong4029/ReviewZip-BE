package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.post.dto.request.PostRequestDto;
import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;
import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.global.response.ApiResponse;
import com.example.ReviewZIP.global.response.code.resultCode.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/posts")
public class PostsController {

    private final PostsService postsService;

    @GetMapping("/hashtags/{hashtagId}")
    @Operation(summary = "해시태그 아이디로 게시글을 찾는 API",description = "해시태그 아이디로 게시글을 찾는 기능, 반환 시 PostPreviewListDto, PostPreviewDto 사용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "hashtagId", description = "해시태그 아이디"),
            @Parameter(name = "page", description = "페이지 번호"),
            @Parameter(name = "size", description = "페이징 사이즈")
    })
    public ApiResponse<PostResponseDto.PostPreviewListDto> searchPostsByHashtagId(@PathVariable Long hashtagId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        Page<Posts> postsPage = postsService.searchPostByHashtag(hashtagId, page, size);
        PostResponseDto.PostPreviewListDto postPreviewListDto = PostsConverter.toPostPreviewListDto(postsPage);
        return ApiResponse.onSuccess(postPreviewListDto);
    }

    // 특정 게시글의 정보 가져오기
    @GetMapping("/{postId}")
    @Operation(summary = "특정 게시글의 정보 가져오기 API",description = "게시글의 id를 이용하여 상세정보 출력, UserInfoDto & ImageListDto & PostInfoDto 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST401", description = "해당 게시물이 존재하지 않습니다.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "postId", description = "게시글의 아이디"),
    })
    public ApiResponse<PostResponseDto.PostInfoDto> getPostInfo(@PathVariable(name = "postId") Long postId){

        PostResponseDto.PostInfoDto postInfoDto = postsService.getPostInfoDto(postId);

        return ApiResponse.onSuccess(postInfoDto);
    }

    @PostMapping
    @Operation(summary = "게시글 생성 API", description = "PostRequestDto, CreatedPostResponseDto 사용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST403", description = "게시글 작성 실패",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<PostResponseDto.CreatedPostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto){
        Posts post = postsService.createPost(postRequestDto);
        return ApiResponse.onSuccess(PostsConverter.toPostResponseDto(post));
    }

    @GetMapping("/random")
    @Operation(summary = "랜덤으로 게시글 3개 가져오기 API", description = "PostInfoDto")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST405", description = "랜덤으로 게시글 3개 가져오기 실패",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<List<PostResponseDto.PostInfoDto>> getRandomPosts(@RequestParam Long userId) {
        List<PostResponseDto.PostInfoDto> randomPostInfoDtos = postsService.getRandomPostInfoDto(userId);

        return ApiResponse.onSuccess(randomPostInfoDtos);
    }

    @GetMapping("/{postId}/users")
    @Operation(summary = "게시물에 공감을 누른 유저리스트를 검색하는 API",description = "게시물 아이디를 조회하여 게시물에 공감을 누른 유저 리스트를 검색, 반환 시 UserPreviewListDto 사용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POSTLIKE403", description = "포스트에 공감을 한 사람이 없음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "postId", description = "게시물 아이디"),
    })
    public ApiResponse<List<PostResponseDto.PostUserLikeDto>> getPostLikeUserList(@PathVariable Long postId) {
        List<Users> usersList = postsService.getPostLikeUserList(postId);
        List<Long> userFollowingList = postsService.getFollowigIdList();

        List<PostResponseDto.PostUserLikeDto> likeAndFollowing = PostsConverter.toPostUserLikeListDto(usersList, userFollowingList);

        return ApiResponse.onSuccess(likeAndFollowing);
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 삭제 API", description = "게시글의 id를 받아 해당하는 게시글 삭제")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST401", description = "게시글이 존재하지 않습니다",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "postId", description = "게시글의 아이디"),
    })
    public ApiResponse<Void> deletePost(@PathVariable(name = "postId") Long postId){
        postsService.deletePost(postId);
        return ApiResponse.onSuccess(null);
    }

    @PostMapping("/{postId}/like")
    @Operation(summary = "포스트에 공감 생성 API",description = "포스트에 공감을 생성하는 기능, 입력 시 PostLikesDto 사용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER408", description = "유저를 찾을 수 없음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST401", description = "게시글을 찾을 수 없음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POSTLIKE401", description = "공감 누르기에 실패",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POSTLIKE402", description = "이미 공감한 게시물",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<SuccessStatus> addPostLike(@PathVariable(name = "postId") Long postId ) {
        postsService.addLike(postId);
        return ApiResponse.onSuccess(SuccessStatus.POST_CHOOSE_LIKE_SUCCESS);
    }

    @DeleteMapping("/{postId}/like")
    @Operation(summary = "포스트에 공감 해제API",description = "포스트에 공감을 해제하는 기능")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POSTLIKE403", description = "존재하지 않는 공감",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<SuccessStatus> postRemoveLike(@PathVariable Long postId) {
        postsService.removeLike(postId, 1L);
        return ApiResponse.onSuccess(SuccessStatus.POST_CANCEL_LIKE_SUCCESS);
    }
}