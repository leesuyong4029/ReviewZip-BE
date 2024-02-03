package com.example.ReviewZIP.domain.scrab;

import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.user.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrabsRepository extends JpaRepository<Scrabs, Long> {
    Page<Scrabs> findAllByUser(Users user, PageRequest pageRequest);

    boolean existsByUserAndPost(Users user, Posts post);

    Scrabs findByUserAndPost(Users user, Posts post);
}
