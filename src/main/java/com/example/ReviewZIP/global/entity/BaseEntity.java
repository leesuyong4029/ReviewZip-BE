package com.example.ReviewZIP.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
<<<<<<< HEAD
=======

>>>>>>> 9a8ae7b (feat: 유저아이디로 유저 팔로우)
import java.time.LocalDate;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @CreatedDate
    @Column(name = "create_at")
    private LocalDate createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDate updatedAt;
<<<<<<< HEAD
}
=======
}
>>>>>>> 9a8ae7b (feat: 유저아이디로 유저 팔로우)
