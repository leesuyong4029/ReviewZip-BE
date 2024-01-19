package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UsersConverter {
    public static UserResponseDto.PostPreviewDto toUserPostPreviewDto(Posts post){
        return UserResponseDto.PostPreviewDto.builder()
                .postId(post.getId())
                .likeNum(post.getPostLikeList().size())
                .scrabNum(post.getScrabList().size())
                .postImageUrl(post.getPostImageList().get(0).getUrl())
                .build();
    }

    public static UserResponseDto.PostPreviewListDto toPostPreviewListDto(Page<Posts> postList){
        List<UserResponseDto.PostPreviewDto> userPostPriviewDtoList = postList.stream()
                .map(UsersConverter::toUserPostPreviewDto).collect(Collectors.toList());

        return UserResponseDto.PostPreviewListDto.builder()
                .isLast(postList.isLast())
                .isFirst(postList.isFirst())
                .totalElements(postList.getTotalElements())
                .totalPage(postList.getTotalPages())
                .listSize(userPostPriviewDtoList.size())
                .postList(userPostPriviewDtoList)
                .build();
    }
}
