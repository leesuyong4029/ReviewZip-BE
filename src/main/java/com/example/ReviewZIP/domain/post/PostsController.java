package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.post.dto.request.PostRequestDto;
import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/posts")
public class PostsController {

    private final PostsService postsService;

    @GetMapping("/{hashtagId}")
    @Operation(summary = "해시태그 아이디로 게시글을 찾는 API",description = "해시태그 아이디로 게시글을 찾는 기능, 반환 시 PostPreviewListDto, PostPreviewDto 사용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "HASHTAG401", description = "해시태그를 찾을 수 없음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
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
}