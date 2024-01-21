package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.postLike.PostLikesRepository;
import com.example.ReviewZIP.domain.scrab.Scrabs;
import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UsersConverter {

    public static UserResponseDto.PostPreviewDto toUserPostPreviewDto(Scrabs scrabs){
        return UserResponseDto.PostPreviewDto.builder()
                .postId(scrabs.getPost().getId())
                .likeNum(scrabs.getPost().getPostLikeList().size())
                .scrabNum(scrabs.getPost().getScrabList().size())
                .postImageUrl(scrabs.getPost().getPostImageList().get(0).getUrl())
                .build();
    }

    public static UserResponseDto.PostPreviewListDto toPostPreviewListDto(Page<Scrabs> scrabList){
        List<UserResponseDto.PostPreviewDto> userPostPriviewDtoList = scrabList.stream()
                .map(UsersConverter::toUserPostPreviewDto).collect(Collectors.toList());

        return UserResponseDto.PostPreviewListDto.builder()
                .isLast(scrabList.isLast())
                .isFirst(scrabList.isFirst())
                .totalElements(scrabList.getTotalElements())
                .totalPage(scrabList.getTotalPages())
                .listSize(userPostPriviewDtoList.size())
                .postList(userPostPriviewDtoList)
                .build();
    }
}
