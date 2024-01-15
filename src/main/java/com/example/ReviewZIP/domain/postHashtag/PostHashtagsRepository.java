package com.example.ReviewZIP.domain.postHashtag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostHashtagsRepository extends JpaRepository<PostHashtags, Long> {
}
