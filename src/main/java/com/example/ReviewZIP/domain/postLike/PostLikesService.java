package com.example.ReviewZIP.domain.postLike;

import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.PostLikesHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostLikesService{

    private final PostLikesRepository postLikesRepository;


    public Page<Users> getUsersByPostId(Long postId, Integer page, Integer size) {
        Page<PostLikes> postLikesList = postLikesRepository.findAllByPostId(postId, PageRequest.of(page, size));

        if (postLikesList.isEmpty()) {
            throw new PostLikesHandler(ErrorStatus.POSTLIKE_NOT_FOUND);
        }
        return new PageImpl<>(
                postLikesList.getContent().stream()
                        .map(PostLikes::getUser)
                        .collect(Collectors.toList()),
                postLikesList.getPageable(),
                postLikesList.getTotalElements()
        );
    }
}
