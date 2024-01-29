package com.example.ReviewZIP.domain.follow;

import com.example.ReviewZIP.domain.user.Users;

public class FollowsConverter {
    public static Follows toFollows(Users sender, Users receiver){
        return Follows.builder()
                .sender(sender)
                .receiver(receiver)
                .build();
    }

}
