package com.example.ReviewZIP.domain.postLike;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostLikesController {

    private final PostLikesService postLikesService;


    @DeleteMapping("/{postId}")
    public ResponseEntity<String> postRemoveLike(@PathVariable Long postId) {
        postLikesService.removeLike(postId, 1L);
        return ResponseEntity.ok("message : " + 1 + " delete " + postId + "'s like");
    }

}
