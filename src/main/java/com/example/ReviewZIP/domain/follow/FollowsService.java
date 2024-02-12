package com.example.ReviewZIP.domain.follow;


import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
import com.example.ReviewZIP.global.response.ApiResponse;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.code.resultCode.SuccessStatus;
import com.example.ReviewZIP.global.response.exception.handler.UsersHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowsService {
    private final FollowsRepository followsRepository;
    private final UsersRepository usersRepository;


    @Transactional
    public ApiResponse<SuccessStatus> createFollowing(Long userId) {
        Users sender = usersRepository.getById(1L);
        Users receiver = usersRepository.findById(userId).orElseThrow(() -> new UsersHandler(ErrorStatus.USER_NOT_FOUND));
        boolean alreadyFollow = followsRepository.existsBySenderAndReceiver(sender, receiver);
        if(alreadyFollow)
            return ApiResponse.onSuccess(SuccessStatus._OK);
        Follows newFollow = FollowsConverter.toFollows(sender, receiver);
        followsRepository.save(newFollow);
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }

    @Transactional
    public ApiResponse<SuccessStatus> unfollowUser(Long userId){
        Users sender = usersRepository.findById(1L).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));
        Users receiver = usersRepository.findById(userId).orElseThrow(()->new  UsersHandler(ErrorStatus.USER_NOT_FOUND));
        Follows unfollow = followsRepository.getBySenderAndReceiver(sender, receiver);
        followsRepository.delete(unfollow);
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }
}
