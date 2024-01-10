package com.example.ReviewZIP.domain.scrab;

import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.global.entity.BaseEntity;
import com.example.ReviewZIP.post.Posts;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "scrabs")
public class Scrabs extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts post_id;
}
