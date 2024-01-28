package com.example.ReviewZIP.domain.postLike;

import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {

    boolean existsByUserAndPost(Users user, Posts post);
}
