package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.image.Images;
import com.example.ReviewZIP.domain.image.ImagesRepository;
import com.example.ReviewZIP.domain.postHashtag.PostHashtags;
import com.example.ReviewZIP.domain.postHashtag.PostHashtagsRepository;
import com.example.ReviewZIP.domain.postLike.PostLikes;
import com.example.ReviewZIP.domain.postLike.PostLikesRepository;
import com.example.ReviewZIP.domain.scrab.Scrabs;
import com.example.ReviewZIP.domain.scrab.ScrabsRepository;
import com.example.ReviewZIP.domain.store.Stores;
import com.example.ReviewZIP.domain.store.StoresRepository;
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
    private final ScrabsRepository scrabsRepository;
    private final PostLikesRepository postLikesRepository;
    private final PostHashtagsRepository postHashtagsRepository;
    private final ImagesRepository imagesRepository;
    private final StoresRepository storesRepository;

    @Transactional
    public void deletePost(Long postId){
        Posts post = postsRepository.findById(postId).orElseThrow(()-> new PostsHandler(ErrorStatus.POST_NOT_FOUND));

        for (Scrabs scrab : post.getScrabList()) {
            scrabsRepository.delete(scrab);
        }

        for (Images images : post.getPostImageList()){
            imagesRepository.delete(images);
        }

        for (PostLikes likes : post.getPostLikeList()){
            postLikesRepository.delete(likes);
        }

        for (PostHashtags hashtags : post.getPostHashtagList()){
            postHashtagsRepository.delete(hashtags);
        }

        for (Stores store : post.getStoreList()){
            storesRepository.delete(store);
        }

        postsRepository.deleteById(post.getId());

    }
}
