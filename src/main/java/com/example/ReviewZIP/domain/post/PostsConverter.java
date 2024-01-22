package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.image.Images;
import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;
import com.example.ReviewZIP.domain.postHashtag.PostHashtags;
import com.example.ReviewZIP.domain.user.Users;

import java.util.List;
import java.util.stream.Collectors;

public class PostsConverter {
    public static PostResponseDto.UserInfoDto toUserInfoDto(Users user){
        return PostResponseDto.UserInfoDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .name(user.getName())
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

        return PostResponseDto.PostInfoDto.builder()
                .postId(post.getId())
                .comment(post.getComment())
                .point(post.getPoint())
                .likeNum(post.getPostLikeList().size())
                .iLike(checkLike)
                .iScrab(checkScrab)
                .hashtags(post.getPostHashtagList().stream().map(PostHashtags::getHashtag).collect(Collectors.toList()))
                .userInfo(userInfoDto)
                .postImages(imageListDto)
                .createdAt(post.getCreatedAt().toLocalDate())
                .build();
    }
}
