package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.Follows;
import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.postLike.PostLikes;
import com.example.ReviewZIP.domain.scrab.Scrabs;
import com.example.ReviewZIP.domain.searchHistory.SearchHistories;
import com.example.ReviewZIP.domain.token.dto.request.SignUpRequestDto;
import com.example.ReviewZIP.domain.userStores.UserStores;
import com.example.ReviewZIP.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Where(clause = "status = 'ENABLED'")
@SQLDelete(sql = "UPDATE reviewzip.users SET status = 'DISABLED' WHERE id = ?")
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

    @Column(unique = true)
    private String social;

    private String password;

    private String phone_num;

    @Column(nullable = false)
    private String nickname;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SearchHistories> searchHistoriesList = new ArrayList<>();

    @OneToMany(mappedBy = "object", cascade = CascadeType.ALL)
    private List<SearchHistories> objectList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private Set<UserStores> userStores = new HashSet<>();

    public static Users toEntity(SignUpRequestDto signUpRequestDto) {
        return Users.builder()
                .name(signUpRequestDto.getName())
                .email(signUpRequestDto.getEmail())
                .password(signUpRequestDto.getPassword())
                .nickname(signUpRequestDto.getNickname())
                .phone_num(signUpRequestDto.getPhoneNum())
                .status(Status.ENABLED)
                .build();
    }
}
