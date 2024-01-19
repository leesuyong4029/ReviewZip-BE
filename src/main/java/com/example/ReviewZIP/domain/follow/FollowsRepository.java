package com.example.ReviewZIP.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowsRepository extends JpaRepository<Follows, Long> {
    Follows getBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
