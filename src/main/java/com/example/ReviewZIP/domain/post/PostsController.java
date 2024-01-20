package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/posts")
public class PostsController {

    private final PostsService postsService;

    @GetMapping("/{hashtagId}")
    public ApiResponse<PostResponseDto.PostPreviewListDto> searchPostsByHashtagId(@PathVariable Long hashtagId, @RequestParam(defaultValue = "0") Integer page) {
        Page<Posts> postsPage = postsService.searchPostByHashtag(hashtagId, page);
        PostResponseDto.PostPreviewListDto postPreviewListDto = PostsConverter.toPostPreviewListDto(postsPage);
        return ApiResponse.onSuccess(postPreviewListDto);
    }
}
