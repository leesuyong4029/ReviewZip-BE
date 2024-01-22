package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostsController {

    private final PostsService postsService;
    @GetMapping("/{postId}")
    public ApiResponse<PostResponseDto.PostInfoDto> getPostInfo(@PathVariable(name = "postId") Long postId){

        PostResponseDto.PostInfoDto postInfoDto = postsService.getPostInfoDto(postId);

        return ApiResponse.onSuccess(postInfoDto);
    }
}
