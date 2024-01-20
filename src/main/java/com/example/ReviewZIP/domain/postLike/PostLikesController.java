package com.example.ReviewZIP.domain.postLike;

import com.example.ReviewZIP.global.response.ApiResponse;
import com.example.ReviewZIP.global.response.code.resultCode.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostLikesController {

    private final PostLikesService postLikesService;


    @DeleteMapping("/{postId}")
    public ApiResponse<SuccessStatus> postRemoveLike(@PathVariable Long postId) {
        postLikesService.removeLike(postId, 1L);
        return ApiResponse.onSuccess(SuccessStatus.POST_CANCEL_LIKE_SUCCESS);
    }

}
