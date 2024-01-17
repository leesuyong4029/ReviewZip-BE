package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.FollowsRepository;
import com.example.ReviewZIP.domain.user.dto.response.OtherInfoResponseDto;
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
    public OtherInfoResponseDto getOtherInfo(Long userId){
        Users other = usersRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("not found user"));

        Integer followingNum = followsRepository.countBySenderId(userId);
        Integer followerNum = followsRepository.countByReceiverId(userId);

        return UsersConverter.toOtherInfoDto(other, followingNum, followerNum);
    }

}
