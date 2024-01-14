package com.example.ReviewZIP.domain.follow;

import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
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
    public void unfollowUser(Long userId){
        Users sender = usersRepository.findById(1L).orElseThrow(()->new IllegalArgumentException("not found sender"));
        Users receiver = usersRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("not found receiver"));

        Follows unfollow = followsRepository.findBySenderIdAndReceiverId(1L, receiver.getId())
                .orElseThrow(()->new IllegalArgumentException("NOT FOUND FOLLOWS TABLE"));

        followsRepository.delete(unfollow);
    }
}
