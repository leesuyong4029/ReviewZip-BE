package com.example.ReviewZIP.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.ReviewZIP.domain.user.Users;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {
    Page<Posts> findAllByUser(Users user, PageRequest pageRequest);

    @Query("SELECT count(p) FROM Posts p WHERE p.user != :user")
    long countByUserNot(@Param("user") Users user);

    Page<Posts> findAllByUserNot(Users user, PageRequest pageRequest);

}
