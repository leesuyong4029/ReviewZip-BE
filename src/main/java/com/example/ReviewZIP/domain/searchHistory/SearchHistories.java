package com.example.ReviewZIP.domain.searchHistory;

import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.user.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "search_histories")
public class SearchHistories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    private String content;

    private SearchType type;
}
