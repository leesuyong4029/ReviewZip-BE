package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.post.dto.request.PostRequestDto;
import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.ReviewZIP.domain.post.dto.request.PostRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostsController {
    private final PostsService postsService;
    private final PostsConverter postsConverter;

    // 특정 게시글의 정보 가져오기
    @GetMapping("/{postId}")
    public ApiResponse<PostResponseDto.PostInfoDto> getPostInfo(@PathVariable(name = "postId") Long postId){

        PostResponseDto.PostInfoDto postInfoDto = postsService.getPostInfoDto(postId);

        return ApiResponse.onSuccess(postInfoDto);
    }

    @PostMapping
    public ApiResponse<PostResponseDto.CreatedPostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto){
        Posts post = postsService.createPost(postRequestDto);
        return ApiResponse.onSuccess(PostsConverter.toPostResponseDto(post));
    }

    @GetMapping("/random")
    public ApiResponse<List<PostResponseDto.PostInfoDto>> getRandomPosts(@RequestParam Long userId) {
        List<PostResponseDto.PostInfoDto> randomPostInfoDtos = postsService.getRandomPostInfoDto(userId);

        return ApiResponse.onSuccess(randomPostInfoDtos);
    }
}