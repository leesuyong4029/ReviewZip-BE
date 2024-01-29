package com.example.ReviewZIP.domain.postHashtag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostHashtagsRepository extends JpaRepository<PostHashtags, Long> {

    Page<PostHashtags> findPostHashtagsById(Long id, PageRequest pageRequest);

}
