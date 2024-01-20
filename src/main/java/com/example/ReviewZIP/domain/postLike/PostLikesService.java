package com.example.ReviewZIP.domain.postLike;

import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.PostLikesHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikesService {

    private final PostLikesRepository postLikesRepository;
    public void removeLike(Long postId, Long userId) {
        PostLikes postLikes = postLikesRepository.findByPostIdAndUserId(postId, userId).orElseThrow(() -> new PostLikesHandler(ErrorStatus.POSTLIKE_NOT_FOUND));
        postLikesRepository.delete(postLikes);
    }
}
