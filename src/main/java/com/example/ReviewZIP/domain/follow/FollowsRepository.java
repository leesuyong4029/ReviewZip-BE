package com.example.ReviewZIP.domain.follow;

import com.example.ReviewZIP.domain.user.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FollowsRepository extends JpaRepository<Follows, Long> {
    Page<Follows> findAllBySender(Users sender, PageRequest pageRequest);
    Page<Follows> findAllByReceiver(Users user, PageRequest pageRequest);

    Follows getBySenderAndReceiver(Users sender, Users receiver);

    Integer countBySenderId(Long userId);

    Integer countByReceiverId(Long userid);

    boolean existsBySenderAndReceiver(Users sender, Users receiver);

    List<Follows> findAllBySender(Users user);
}
