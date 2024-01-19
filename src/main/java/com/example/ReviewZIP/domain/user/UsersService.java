package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.Follows;
import com.example.ReviewZIP.domain.follow.FollowsRepository;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.FollowsHandler;
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
    private final FollowsRepository followsRepository;

    @Transactional
    public Page<Follows> getOtherFollowerList(Long userId, Integer page, Integer size){
        Users receiver = usersRepository.findById(userId).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));
        Page<Follows> FollowsPage = followsRepository.findAllByReceiver(receiver, PageRequest.of(page, size));
        if(FollowsPage.isEmpty()){
            throw new FollowsHandler(ErrorStatus.FOLLOWER_LIST_NOT_FOUND);
        }
        return FollowsPage;
    }
}
