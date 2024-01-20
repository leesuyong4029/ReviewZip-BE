package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.FollowsRepository;
import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
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
    private final FollowsRepository followsRepository;
    @Transactional
    public UserResponseDto.OtherInfoDto getOtherInfo(Long userId){
        // 사용자 임의 처리, 1L 가정
        Users me = usersRepository.getById(1L);
        Users other = usersRepository.findById(userId)
                .orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));

        Integer followingNum = followsRepository.countBySenderId(userId);
        Integer followerNum = followsRepository.countByReceiverId(userId);
        boolean isFollowing = followsRepository.existsBySenderAndReceiver(me, other);

        return UsersConverter.toOtherInfoDto(other, followingNum, followerNum, isFollowing);
    }

}
