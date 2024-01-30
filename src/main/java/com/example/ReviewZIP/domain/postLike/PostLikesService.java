package com.example.ReviewZIP.domain.postLike;

import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.PostLikesHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.PostLikesHandler;
import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.post.PostsRepository;
import com.example.ReviewZIP.domain.postLike.dto.request.PostLikesRequestDto;
import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.PostLikesHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostLikesService{

    private final PostLikesRepository postLikesRepository;
    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;

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

    @Transactional
    public void removeLike(Long postId, Long userId) {
        PostLikes postLikes = postLikesRepository.findByPostIdAndUserId(postId, userId).orElseThrow(() -> new PostLikesHandler(ErrorStatus.POSTLIKE_NOT_FOUND));
        postLikesRepository.delete(postLikes);
    }
    @Transactional
    public void addLike(PostLikesRequestDto.PostLikesDto postLikesDto) {
        Users user = usersRepository.findById(postLikesDto.getUserId()).orElseThrow(() -> new PostLikesHandler(ErrorStatus.USER_NOT_FOUND));
        Posts post = postsRepository.findById(postLikesDto.getPostId()).orElseThrow(() -> new PostLikesHandler(ErrorStatus.POST_NOT_FOUND));

        if (postLikesRepository.existsByUserAndPost(user, post)) {

            throw new PostLikesHandler(ErrorStatus.POSTLIKE_ALREADY_EXISTS);
        }
        PostLikes postLikes = PostLikes.builder()
                .post(post)
                .user(user)
                .build();

        try {
            PostLikes result = postLikesRepository.save(postLikes);
        } catch (Exception e) {
            throw new PostLikesHandler(ErrorStatus.POSTLIKE_CREATE_FAIL);
        }

    }
}
