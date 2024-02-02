package com.example.ReviewZIP.domain.postLike;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {
    Optional<PostLikes> findByPostIdAndUserId(Long postId, Long userId);

    @Query("SELECT pl.user FROM PostLikes pl WHERE pl.post.id = :postId")
    List<Users> findUsersByPostId(@Value("postId") Long postId);

    boolean existsByUserAndPost(Users user, Posts post);
}
