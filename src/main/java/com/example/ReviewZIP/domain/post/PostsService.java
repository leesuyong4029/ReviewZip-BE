package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.PostsHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public void deletePost(Long postId){
        Posts post = postsRepository.findById(postId).orElseThrow(()-> new PostsHandler(ErrorStatus.POST_NOT_FOUND));

        postsRepository.deleteById(post.getId());

    }
}
