package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;
import com.example.ReviewZIP.domain.image.Images;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostsConverter {
    public static PostResponseDto toPostResponseDto(Posts post) {
        PostResponseDto responseDto = new PostResponseDto();
        responseDto.setPostId(post.getId());
        responseDto.setComment(post.getComment());
        responseDto.setPoint(post.getPoint());
        responseDto.setUserId(post.getUser().getId());

        List<Long> imageIds = post.getPostImageList().stream()
                .map(Images::getId)
                .collect(Collectors.toList());
        responseDto.setImageIds(imageIds);

        return responseDto;
    }
}