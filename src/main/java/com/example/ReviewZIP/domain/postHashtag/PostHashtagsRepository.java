package com.example.ReviewZIP.domain.postHashtag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface PostHashtagsRepository extends JpaRepository<PostHashtags, Long> {

    Page<PostHashtags> findPostHashtagsById(Long id, PageRequest pageRequest);


    @Query("SELECT ph FROM PostHashtags ph WHERE ph.hashtag LIKE %:name%")
    List<PostHashtags> findByNameContaining(@Param("name") String name);

}
