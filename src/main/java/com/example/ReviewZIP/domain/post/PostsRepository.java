package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.user.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    Page<Posts> findAllByUser(Users user, PageRequest pageRequest);
}
