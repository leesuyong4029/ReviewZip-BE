package com.example.ReviewZIP.domain.image.dto;

import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.global.entity.BaseEntity;
import com.example.ReviewZIP.post.Posts;
import jakarta.persistence.*;

public class Images extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts post_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String url;
}
