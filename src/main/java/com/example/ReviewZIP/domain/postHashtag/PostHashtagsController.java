package com.example.ReviewZIP.domain.postHashtag;


import com.example.ReviewZIP.domain.postHashtag.dto.response.PostHashtagResponseDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import com.example.ReviewZIP.global.response.code.resultCode.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/hashtags")
public class PostHashtagsController {

    private final PostHashtagsService postHashtagsService;

    @GetMapping("/{postId}")
    @Operation(summary = "게시물에 해시태그 추가 API", description = "게시물에 해시태그를 추가")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name="postId", description = "작성하는 게시물 ID"),
            @Parameter(name="query", description = "해시태그 이름")
    })
    public ApiResponse<SuccessStatus> addHashtags(@RequestParam String query, @PathVariable Long postId) {
        postHashtagsService.addHashtags(query, postId);

        return ApiResponse.onSuccess(SuccessStatus._OK);
    }

    @GetMapping("/search")
    @Operation(summary = "해시태그 이름으로 해시태그를 검색 API", description = "해시태그 이름으로 검색 시 해시태그 리스트를 반환하고 검색기록에 저장, PostHashtagsPreviewDto 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name="hashtag", description = "해시태그 이름")
    })
    public ApiResponse<List<PostHashtagResponseDto.PostHashtagsPreviewDto>> searchHashtags(@RequestParam String hashtag) {
        List<PostHashtags> postHashtagsList = postHashtagsService.searchHashtagsByName(hashtag);
        return ApiResponse.onSuccess(PostHashtagsConverter.toPostHashtagsPreviewListDto(postHashtagsList));
    }
}
