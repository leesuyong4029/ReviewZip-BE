package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostsContoroller {
    private final PostsService postsService;

    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(@PathVariable(name = "postId") Long postId){
        return ApiResponse.onSuccess(null);
    }

}
