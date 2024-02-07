package com.example.ReviewZIP.domain.userStores;

import com.example.ReviewZIP.domain.user.Users;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_stores")
public class UserStores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double latitude;


}
