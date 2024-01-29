package com.example.ReviewZIP.domain.post;

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
}
