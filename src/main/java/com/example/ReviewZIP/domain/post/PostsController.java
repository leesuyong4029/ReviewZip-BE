package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.post.dto.request.PostRequestDto;
import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostsController {
    private final PostsService postsService;
    private final PostsConverter postsConverter;

    @PostMapping
    public ApiResponse<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto){
        Posts post = postsService.createPost(postRequestDto);
        return ApiResponse.onSuccess(PostsConverter.toPostResponseDto(post));
    }
}