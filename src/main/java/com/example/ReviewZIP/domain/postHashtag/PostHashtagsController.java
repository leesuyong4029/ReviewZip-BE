package com.example.ReviewZIP.domain.postHashtag;


import com.example.ReviewZIP.domain.postHashtag.dto.response.PostHashtagResponseDto;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/hashtags")
public class PostHashtagsController {

    private final PostHashtagsService postHashtagsService;


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

    @GetMapping("/{hashtagId}")
    @Operation(summary = "해시태그 아이디로 해시태그 조회 API", description = "해시태그 id로 해당 해시태그 객체 반환, PostHashtagsPreviewDto 이용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "HASHTAG401", description = "해당하는 해시태그가 존재하지 않음.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name="hashtagId", description = "해시태그 아이디")
    })
    public ApiResponse<PostHashtagResponseDto.PostHashtagsPreviewDto> getHashtag(@PathVariable(name="hashtagId")Long hashtagId){
        PostHashtags hashtag = postHashtagsService.getPostHashtag(hashtagId);
        return ApiResponse.onSuccess(PostHashtagsConverter.toPostHashtagsPreviewDto(hashtag));
    }
}
