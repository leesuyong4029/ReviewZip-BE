package com.example.ReviewZIP.domain.postLike;

import com.example.ReviewZIP.domain.postLike.dto.request.PostLikesRequestDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import com.example.ReviewZIP.global.response.code.resultCode.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts/like")
public class PostLikesController {

    private final PostLikesService postLikesService;

    @PostMapping("/")
    public ApiResponse<SuccessStatus> postAddLike(@RequestBody PostLikesRequestDto.PostLikesDto postLikesDto) {
        postLikesService.addLike(postLikesDto);
        return ApiResponse.onSuccess(SuccessStatus.POST_CHOOSE_LIKE_SUCCESS);
    }

}
