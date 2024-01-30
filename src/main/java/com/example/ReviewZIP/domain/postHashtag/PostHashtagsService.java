package com.example.ReviewZIP.domain.postHashtag;

import com.example.ReviewZIP.domain.post.PostsRepository;
import com.example.ReviewZIP.global.redis.RedisService;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.PostsHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostHashtagsService {

    private final PostHashtagsRepository postHashtagsRepository;
    private final RedisService redisService;
    private final PostsRepository postsRepository;
    @Transactional
    public void addHashtags(String query, Long postId) {
        redisService.addHashtag(query);

        PostHashtags postHashtags = PostHashtags.builder()
                .hashtag(query)
                .post(postsRepository.findById(postId).orElseThrow( () -> new PostsHandler(ErrorStatus.POST_NOT_FOUND)))
                .build();
        postHashtagsRepository.save(postHashtags);
    }
}
