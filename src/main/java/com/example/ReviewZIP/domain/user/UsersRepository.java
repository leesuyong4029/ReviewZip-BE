package com.example.ReviewZIP.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query("SELECT u FROM Users u WHERE u.name LIKE :name%")
    List<Users> findByName(String name);

    @Query("SELECT u FROM Users u WHERE u.nickname LIKE :nickname%")
    List<Users> findByNickname(String nickname);

    boolean existsBySocial(String id);

    Users getBySocial(String userId);
}
