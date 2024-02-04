package com.example.ReviewZIP.domain.postHashtag;

import com.example.ReviewZIP.domain.post.PostsRepository;
import com.example.ReviewZIP.domain.searchHistory.SearchHistories;
import com.example.ReviewZIP.domain.searchHistory.SearchHistoriesRepository;
import com.example.ReviewZIP.domain.searchHistory.SearchType;
import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
import com.example.ReviewZIP.global.redis.RedisService;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.PostHashtagsHandler;
import com.example.ReviewZIP.global.response.exception.handler.PostsHandler;
import com.example.ReviewZIP.global.response.exception.handler.UsersHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostHashtagsService {

    private final PostHashtagsRepository postHashtagsRepository;
    private final RedisService redisService;
    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;
    private final SearchHistoriesRepository searchHistoriesRepository;

    @Transactional
    public void addHashtags(String query, Long postId) {
        redisService.addHashtag(query);

        PostHashtags postHashtags = PostHashtags.builder()
                .hashtag(query)
                .post(postsRepository.findById(postId).orElseThrow( () -> new PostsHandler(ErrorStatus.POST_NOT_FOUND)))
                .build();
        postHashtagsRepository.save(postHashtags);
    }

    public List<PostHashtags> searchHashtagsByName(String hashtag) {
        List<PostHashtags> hashtagsList = postHashtagsRepository.findByNameContaining(hashtag);

        return hashtagsList;
    }
}
