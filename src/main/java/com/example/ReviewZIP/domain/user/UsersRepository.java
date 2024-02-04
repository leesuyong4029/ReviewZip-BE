package com.example.ReviewZIP.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query("SELECT u FROM Users u WHERE u.name LIKE :name%")
    Page<Users> findByName(String name, Pageable pageable);

    @Query("SELECT u FROM Users u WHERE u.name LIKE :nickname%")
    Page<Users> findByNickname(String nickname, Pageable pageable);

    boolean existsBySocial(String id);

    Users getBySocial(String userId);
}
