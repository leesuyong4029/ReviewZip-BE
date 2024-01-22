package com.example.ReviewZIP.domain.user;


import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.post.PostsRepository;
import com.example.ReviewZIP.domain.scrab.Scrabs;
import com.example.ReviewZIP.domain.scrab.ScrabsRepository;
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
    private final ScrabsRepository scrabsRepository;

    @Transactional
    public Page<Posts> getOtherPostList(Long userId, Integer size, Integer page){
        Users user = usersRepository.findById(userId).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));
        Page<Posts> UserPage = postsRepository.findAllByUser(user, PageRequest.of(page, size));
      
        return UserPage;
    }

    

    @Transactional
    public Page<Scrabs> getOtherScrabList(Long userId, Integer page, Integer size){
        Users user = usersRepository.findById(userId).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));
        Page<Scrabs> UserPage = scrabsRepository.findAllByUser(user, PageRequest.of(page, size));

        return UserPage;
    }
}
