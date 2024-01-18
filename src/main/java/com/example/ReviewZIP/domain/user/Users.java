package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.Follows;
import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.postLike.PostLikes;
import com.example.ReviewZIP.domain.scrab.Scrabs;
import com.example.ReviewZIP.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String user_id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone_num;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profileUrl;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Follows> followingList = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Follows> followerList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Scrabs> scrabList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Posts> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PostLikes> postLikeList = new ArrayList<>();
}
