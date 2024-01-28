package com.example.ReviewZIP.domain.postHashtag;


import com.example.ReviewZIP.global.response.ApiResponse;
import com.example.ReviewZIP.global.response.code.resultCode.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/hashtags")
public class PostHashtagsController {

    private final PostHashtagsService postHashtagsService;

    @GetMapping("/{postId}")
    public ApiResponse<SuccessStatus> addHashtags(@RequestParam String query, @PathVariable Long postId) {
        postHashtagsService.addHashtags(query, postId);

        return ApiResponse.onSuccess(SuccessStatus._OK);
    }
}
