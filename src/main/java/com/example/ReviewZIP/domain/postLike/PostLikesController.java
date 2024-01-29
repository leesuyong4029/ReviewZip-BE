package com.example.ReviewZIP.domain.postLike;

import com.example.ReviewZIP.global.response.ApiResponse;
import com.example.ReviewZIP.global.response.code.resultCode.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostLikesController {

    private final PostLikesService postLikesService;


    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글에 공감 해제 API",description = "게시글에 공감을 해제하는 기능, DTO 사용 X")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POSTLIKE403", description = "게시글에 공감이 존재하지 않음",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "postId", description = "게시글 아이디"),
    })
    public ApiResponse<SuccessStatus> removePostLike(@PathVariable Long postId) {
        postLikesService.removeLike(postId, 1L);
        return ApiResponse.onSuccess(SuccessStatus.POST_CANCEL_LIKE_SUCCESS);
    }

}
