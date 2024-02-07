package com.example.ReviewZIP.domain.postHashtag;

import com.example.ReviewZIP.domain.post.PostsRepository;
import com.example.ReviewZIP.domain.searchHistory.SearchHistoriesRepository;
import com.example.ReviewZIP.domain.user.UsersRepository;
import com.example.ReviewZIP.global.redis.RedisService;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.PostHashtagsHandler;
import com.example.ReviewZIP.global.response.exception.handler.PostsHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostHashtagsService {

    private final PostHashtagsRepository postHashtagsRepository;

    public List<PostHashtags> searchHashtagsByName(String hashtag) {
        List<PostHashtags> hashtagsList = postHashtagsRepository.findByNameContaining(hashtag);

        return hashtagsList;
    }

    public PostHashtags getPostHashtag(Long hashtagId){
        return postHashtagsRepository.findById(hashtagId).orElseThrow(()->new PostHashtagsHandler(ErrorStatus.HASHTAG_NOT_FOUND));
    }
}
