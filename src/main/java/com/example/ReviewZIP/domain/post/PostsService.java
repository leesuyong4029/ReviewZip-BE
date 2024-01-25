package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;
import com.example.ReviewZIP.domain.postLike.PostLikesRepository;
import com.example.ReviewZIP.domain.scrab.ScrabsRepository;
import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.PostsHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostsService {
    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;
    private final PostLikesRepository postLikesRepository;
    private final ScrabsRepository scrabsRepository;

    @Transactional
    public List<PostResponseDto.PostInfoDto> getRandomPostInfoDto(Long userId) {
        Users user = usersRepository.getById(userId);

        long qty = postsRepository.countByUserNot(user);

        if (qty < 3) {
            throw new PostsHandler(ErrorStatus.POST_RANDOM_FAIL);
        }

        Set<Integer> randomIndices = new HashSet<>();
        while (randomIndices.size() < 3) {
            int idx = (int)(Math.random() * qty);
            randomIndices.add(idx);
        }

        List<PostResponseDto.PostInfoDto> randomPostInfoDtos = new ArrayList<>();
        for (Integer idx : randomIndices) {
            Page<Posts> postPage = postsRepository
                    .findAllByUserNot(
                            user,
                            PageRequest.of(idx, 1)
                    );

            if (postPage.hasContent()) {
                Posts post = postPage.getContent().get(0);
                boolean checkLike = postLikesRepository.existsByUserAndPost(user, post);
                boolean checkScrab = scrabsRepository.existsByUserAndPost(user, post);
                randomPostInfoDtos.add(PostsConverter.toPostInfoResultDto(post, checkLike, checkScrab));
            }
        }

        return randomPostInfoDtos;
    }
}
