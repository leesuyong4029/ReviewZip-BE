package com.example.ReviewZIP.domain.post;
import com.example.ReviewZIP.domain.image.Images;
import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.postHashtag.PostHashtags;
import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
    public static PostResponseDto.UserInfoDto toUserInfoDto(Users user){
        return PostResponseDto.UserInfoDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .profileUrl(user.getProfileUrl())
                .build();
    }

    public static PostResponseDto.ImageListDto toImageListDto(Images image){
        return PostResponseDto.ImageListDto.builder()
                .id(image.getId())
                .url(image.getUrl())
                .build();
    }
    public static PostResponseDto.PostInfoDto toPostInfoResultDto(Posts post, boolean checkLike, boolean checkScrab){
        PostResponseDto.UserInfoDto userInfoDto = toUserInfoDto(post.getUser());

        List<PostResponseDto.ImageListDto> imageListDto = post.getPostImageList().stream()
                .map(PostsConverter::toImageListDto).collect(Collectors.toList());

        LocalDateTime createdAt = post.getCreatedAt() != null ? post.getCreatedAt() : null;

        return PostResponseDto.PostInfoDto.builder()
                .postId(post.getId())
                .comment(post.getComment())
                .point(post.getPoint())
                .likeNum(post.getPostLikeList().size())
                .checkLike(checkLike)
                .checkScrab(checkScrab)
                .hashtags(post.getPostHashtagList().stream().map(PostHashtags::getHashtag).collect(Collectors.toList()))
                .userInfo(userInfoDto)
                .postImages(imageListDto)
                .createdAt(createdAt)
                .build();
    }
}


