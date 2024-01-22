package com.example.ReviewZIP.domain.follow;

import com.example.ReviewZIP.domain.user.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowsRepository extends JpaRepository<Follows, Long> {
    Page<Follows> findAllByReceiver(Users user, PageRequest pageRequest);
}
