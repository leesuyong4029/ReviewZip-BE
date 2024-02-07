package com.example.ReviewZIP.domain.postHashtag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PostHashtagsRepository extends JpaRepository<PostHashtags, Long> {

    List<PostHashtags> findAllByHashtag(String hashtag);

    @Query("SELECT ph FROM PostHashtags ph WHERE ph.hashtag LIKE %:name% GROUP BY ph.hashtag"  )
    List<PostHashtags> findByNameContaining(@Param("name") String name);

}
