package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.post.dto.request.PostRequestDto;
import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;
import com.example.ReviewZIP.domain.postHashtag.PostHashtags;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/posts")
public class PostsController {

    private final PostsService postsService;

    @GetMapping("/{postId}/hashtags")
    @Operation(summary = "게시물에 해시태그 추가 API", description = "게시물에 해시태그를 추가")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name="postId", description = "작성하는 게시물 ID"),
            @Parameter(name="query", description = "해시태그 이름")
    })
    public ApiResponse<SuccessStatus> addHashtags(@RequestParam String query, @PathVariable Long postId) {
        postsService.addHashtags(query, postId);

        return ApiResponse.onSuccess(SuccessStatus._OK);
    }

    @GetMapping("/hashtags/{hashtagId}")
    @Operation(summary = "해시태그 아이디로 게시글을 찾는 API",description = "해시태그 아이디로 게시글을 찾는 기능, 반환 시 PostPreviewListDto, PostPreviewDto 사용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST403", description = "일치하는 해시태그가 존재하지 않습니다.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "hashtagId", description = "해시태그 아이디"),
    })
    public ApiResponse<List<PostResponseDto.PostInfoDto>> searchPostsByHashtagId(@PathVariable Long hashtagId) {
        List<PostHashtags> postList = postsService.searchPostByHashtag(hashtagId);
        List<PostResponseDto.PostInfoDto> getPostInfoDtoList = postsService.getPostInfoDtoList(postList);
        return ApiResponse.onSuccess(getPostInfoDtoList);
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

    @GetMapping("/random-one")
    @Operation(summary = "랜덤으로 게시글 1개 가져오기 API", description = "PostInfoDto")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST405", description = "사용자가 작성하지 않은 게시글이 필요함.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<PostResponseDto.PostInfoDto> getRandomPost() {
        PostResponseDto.PostInfoDto randomPostInfoDto = postsService.getOneRandomPostInfoDto(1L);

        return ApiResponse.onSuccess(randomPostInfoDto);
    }

    @GetMapping("/random-three")
    @Operation(summary = "랜덤으로 게시글 3개 가져오기 API", description = "PostInfoDto")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST405", description = "사용자가 작성하지 않은 게시글이 필요함.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<List<PostResponseDto.PostInfoDto>> getRandomPosts() {
        List<PostResponseDto.PostInfoDto> randomPostInfoDtos = postsService.getThreeRandomPostsInfo(1L);

        return ApiResponse.onSuccess(randomPostInfoDtos);
    }

    @GetMapping("/{postId}/users")
    @Operation(summary = "게시물에 공감을 누른 유저리스트를 검색하는 API",description = "게시물 아이디를 조회하여 게시물에 공감을 누른 유저 리스트를 검색, 반환 시 UserPreviewListDto 사용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "postId", description = "게시물 아이디"),
    })
    public ApiResponse<List<PostResponseDto.PostUserLikeDto>> getPostLikeUserList(@PathVariable Long postId) {
        List<Users> postLikeUserList = postsService.getPostLikeUserList(postId);
        List<Long> userFollowingList = postsService.getFollowigIdList();

        List<PostResponseDto.PostUserLikeDto> postLikeList = PostsConverter.toPostUserLikeListDto(postLikeUserList, userFollowingList);

        return ApiResponse.onSuccess(postLikeList);
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
    public ApiResponse<SuccessStatus> deletePost(@PathVariable(name = "postId") Long postId){
        postsService.deletePost(postId);
        return ApiResponse.onSuccess(SuccessStatus._OK);
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

        return postsService.addLike(postId);
    }

    @DeleteMapping("/{postId}/like")
    @Operation(summary = "포스트에 공감 해제API",description = "포스트에 공감을 해제하는 기능")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POSTLIKE403", description = "존재하지 않는 공감",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<SuccessStatus> removePostLike(@PathVariable Long postId) {
        postsService.removeLike(postId, 1L);
        return ApiResponse.onSuccess(SuccessStatus.POST_CANCEL_LIKE_SUCCESS);
    }

    @PostMapping("/{postid}/scrabs")
    @Operation(summary = "게시글 스크랩하기 API",description = "post의 id를 받아 스크랩하는 기능")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST401", description = "해당하는 게시글이 존재하지 않음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<SuccessStatus> createScrabs(@PathVariable(name = "postid")Long postId){
        return postsService.createScrabs(postId);
    }

    @DeleteMapping("/{postid}/scrabs")
    @Operation(summary = "게시글 스크랩 취소 API",description = "post의 id를 받아 기존에 있던 scrab을 취소하는 기능")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST401", description = "해당하는 게시글이 존재하지 않음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<SuccessStatus> deleteScrabs(@PathVariable(name = "postid")Long postId){
        postsService.deleteScrabs(postId);
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }
}
