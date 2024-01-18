package com.example.ReviewZIP.domain.follow;


import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.FollowsHandler;
import com.example.ReviewZIP.global.response.exception.handler.UsersHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowsService {
    private final FollowsRepositoy followsRepositoy;
    private final UsersRepository usersRepository;


    @Transactional
    public Follows createFollowing(Long userId){
        Users sender = usersRepository.findById(1L).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));
        Users receiver = usersRepository.findById(userId).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));

        // 팔로우가 이미 존재하는지 존재확인
        boolean existsCheck = followsRepositoy.existsBySenderAndReceiver(sender, receiver);
        if(existsCheck){
            throw new FollowsHandler(ErrorStatus.FOLLOW_ALREADY_EXISTS);
        }

        Follows newFollow = FollowsConverter.toFollows(sender, receiver);
        return followsRepositoy.save(newFollow);
    }
}
