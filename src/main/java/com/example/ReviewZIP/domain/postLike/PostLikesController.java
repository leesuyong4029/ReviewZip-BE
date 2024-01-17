package com.example.ReviewZIP.domain.postLike;

import com.example.ReviewZIP.domain.postLike.dto.request.PostLikesRequestDTO;
import com.example.ReviewZIP.domain.postLike.dto.response.PostLikesResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts/like")
public class PostLikesController {

    private final PostLikesService postLikesService;

    @PostMapping("/")
    public ResponseEntity<PostLikesResponseDTO.PostLikesResultDTO> postAddLike(@RequestBody PostLikesRequestDTO.PostLikesDTO postLikesDTO) {
        return ResponseEntity.ok(postLikesService.addLike(postLikesDTO));
    }

}
