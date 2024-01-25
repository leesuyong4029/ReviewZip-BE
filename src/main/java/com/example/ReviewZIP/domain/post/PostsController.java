package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostsController {
    private final PostsService postsService;
    @GetMapping("/random")
    public ApiResponse<List<PostResponseDto.PostInfoDto>> getRandomPosts(@RequestParam Long userId) {
        List<PostResponseDto.PostInfoDto> randomPostInfoDtos = postsService.getRandomPostInfoDto(userId);

        return ApiResponse.onSuccess(randomPostInfoDtos);
    }
}
