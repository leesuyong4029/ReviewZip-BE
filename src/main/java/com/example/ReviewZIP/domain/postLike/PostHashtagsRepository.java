package com.example.ReviewZIP.domain.postLike;

import com.example.ReviewZIP.domain.postHashtag.PostHashtags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostHashtagsRepository extends JpaRepository<PostHashtags, Long> {
}
