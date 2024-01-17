package com.example.ReviewZIP.domain.postLike;

import com.example.ReviewZIP.domain.postLike.dto.response.PostLikesResponseDTO;

public class PostLikesConverter {

    public static PostLikesResponseDTO.PostLikesResultDTO convertToPostLikesDTO(PostLikes postLikes) {
        return PostLikesResponseDTO.PostLikesResultDTO.builder()
                .id(postLikes.getId())
                .postId(postLikes.getPost().getId())
                .userId(postLikes.getUser().getId())
                .build();
    }
}