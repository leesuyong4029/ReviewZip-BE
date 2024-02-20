package com.example.ReviewZIP.domain.store;

import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoresRepository extends JpaRepository<Stores, Long> {
    Optional<Stores> findByPost(Posts post);
}
