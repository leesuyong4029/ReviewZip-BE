package com.example.ReviewZIP.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowsRepositoy extends JpaRepository<Follows, Long> {
}
