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
    private final FollowsRepositoy followsRepositoy;
    private final UsersRepository usersRepository;


    @Transactional
    public Follows createFollowing(Long userId){
        Users sender = usersRepository.findById(1L).orElseThrow(()->new IllegalArgumentException("sender doesn't exist"));
        Users receiver = usersRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("receiver doesn't exist"));
        Follows newFollow = FollowsConverter.toFollows(sender, receiver);

        return followsRepositoy.save(newFollow);
    }
}
