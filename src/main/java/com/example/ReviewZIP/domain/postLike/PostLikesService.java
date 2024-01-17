package com.example.ReviewZIP.domain.postLike;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikesService {

    private final PostLikesRepository postLikesRepository;
    public void removeLike(Long postId, Long userId) {
        PostLikes postLikes = postLikesRepository.findByPostIdAndUserId(postId, userId).orElseThrow();
        postLikesRepository.delete(postLikes);
    }
}
