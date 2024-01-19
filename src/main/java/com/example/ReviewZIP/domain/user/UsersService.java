package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.post.PostsRepository;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.UsersHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersService {
    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;
    public Page<Posts> getUserPostList(Integer page, Integer size ){
        // 유저의 값은 1L이라 가정. 가져올땐 로그인 후에 사용자의 id값을 가져옴.
        Users user = usersRepository.findById(1L).orElseThrow(()-> new UsersHandler(ErrorStatus.USER_NOT_FOUND));

        Page<Posts> PostPage = postsRepository.findAllByUser(user, PageRequest.of(page, size));

        return PostPage;

    }
}
