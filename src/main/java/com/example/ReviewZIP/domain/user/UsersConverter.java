package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.postLike.PostLikesRepository;
import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UsersConverter {
    public static UserResponseDto.PostPreviewDto toPostPreviewDto(Posts post){
        return UserResponseDto.PostPreviewDto.builder()
                .postId(post.getId())
                .postImageUrl(post.getPostImageList().get(0).getUrl())
                .likeNum(post.getPostLikeList().size())
                .scrabNum(post.getScrabList().size())
                .build();
    }

    public static UserResponseDto.PostPreviewListDto toPostPreviewListDto(Page<Posts> postList){
        List<UserResponseDto.PostPreviewDto> postPreviewDtoList = postList.stream()
                .map(UsersConverter::toPostPreviewDto).collect(Collectors.toList());

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
