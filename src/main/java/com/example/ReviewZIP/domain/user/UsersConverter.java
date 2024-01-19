package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.image.Images;
import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UsersConverter {
    public static UserResponseDto.PostPreviewDto postPreviewDto(Posts post){
        return UserResponseDto.PostPreviewDto.builder()
                .postId(post.getId())
                .comment(post.getComment())
                .point(post.getPoint())
                .likesNum(post.getPostLikeList().size())
                .imageUrl(post.getPostImageList().stream()
                        .map(Images::getUrl)
                        .collect(Collectors.toList()))
                .createdAt(post.getCreatedAt())
                .build();
    }

    public static UserResponseDto.PostPreviewListDto postPreviewListDto(Page<Posts> postList){
        List<UserResponseDto.PostPreviewDto> postPreviewDtoList = postList.stream()
                .map(UsersConverter::postPreviewDto).collect(Collectors.toList());

        return UserResponseDto.PostPreviewListDto.builder()
                .isLast(postList.isLast())
                .isFirst(postList.isFirst())
                .totalPage(postList.getTotalPages())
                .totalElements(postList.getTotalElements())
                .listSize(postPreviewDtoList.size())
                .postList(postPreviewDtoList)
                .build();
    }

}
