package com.example.ReviewZIP.domain.userStores;

import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_stores")
public class UserStores extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address_name;

    @Column(nullable = false)
    private String road_address_name;

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private String latitude;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
