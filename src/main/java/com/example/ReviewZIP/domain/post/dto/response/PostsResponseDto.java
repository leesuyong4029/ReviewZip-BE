package com.example.ReviewZIP.domain.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostsResponseDto {
    private Long userId;
    private Long imageId;
    private String comment;
    private Double point;
}
