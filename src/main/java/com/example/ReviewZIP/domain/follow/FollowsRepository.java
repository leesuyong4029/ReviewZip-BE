package com.example.ReviewZIP.domain.follow;

import com.example.ReviewZIP.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowsRepository extends JpaRepository<Follows, Long> {
    Integer countBySenderId(Long userId);

    Integer countByReceiverId(Long userid);

    boolean existsBySenderAndReceiver(Users sender, Users receiver);
}
