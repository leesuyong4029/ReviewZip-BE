package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.post.PostsRepository;
import com.example.ReviewZIP.domain.postLike.PostLikesRepository;
import com.example.ReviewZIP.domain.scrab.ScrabsRepository;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.UsersHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersService {
    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;
    private final ScrabsRepository scrabsRepository;
    private final PostLikesRepository postLikesRepository;

    @Transactional
    public void deleteUser(Long userId){
        Users user = usersRepository.findById(userId).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));

        usersRepository.deleteById(userId);

    }
}
