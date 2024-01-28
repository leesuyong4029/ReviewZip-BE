package com.example.ReviewZIP.domain.scrab;

import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Repository
public interface ScrabsRepository extends JpaRepository<Scrabs, Long> {
    boolean existsByUserAndPost(Users user, Posts post);

    Page<Scrabs> findAllByUser(Users user, PageRequest pageRequest);

}
