package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;
import com.example.ReviewZIP.domain.image.Images;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostsConverter {
    public static PostResponseDto.CreatedPostResponseDto toPostResponseDto(Posts post) {
        List<Long> imageIds = post.getPostImageList().stream()
                .map(Images::getId)
                .collect(Collectors.toList());

        return PostResponseDto.CreatedPostResponseDto.builder()
                .postId(post.getId())
                .comment(post.getComment())
                .point(post.getPoint())
                .userId(post.getUser().getId())
                .imageIds(imageIds)
                .build();
    }
}