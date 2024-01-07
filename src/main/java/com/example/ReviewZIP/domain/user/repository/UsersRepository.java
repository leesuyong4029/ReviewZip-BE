package com.example.ReviewZIP.domain.user.repository;
import com.example.ReviewZIP.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findUsersById(Long id);

}
