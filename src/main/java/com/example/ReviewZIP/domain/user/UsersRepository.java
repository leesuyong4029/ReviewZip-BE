package com.example.ReviewZIP.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Page<Users> findByName(String name, Pageable pageable);
    Page<Users> findByNickname(String nickname, Pageable pageable);

    boolean existsBySocial(String id);

    Users getBySocial(String userId);
}
