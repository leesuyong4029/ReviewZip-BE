package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class PostsConverter {

    public static PostResponseDto.PostPreviewDto toPostPreviewDto(Posts post) {
        return PostResponseDto.PostPreviewDto.builder()
                .id(post.getId())
                .likeNum(post.getPostLikeList().size())
                .scrabNum(post.getScrabList().size())
                .imageUrl(post.getPostImageList().get(0).getUrl())
                .build();
    }

    public static PostResponseDto.PostPreviewListDto toPostPreviewListDto(Page<Posts> postList) {
        List<PostResponseDto.PostPreviewDto> postPreviewDtoList = postList.stream()
                .map(PostsConverter::toPostPreviewDto).collect(Collectors.toList());

        return PostResponseDto.PostPreviewListDto.builder()
                .isLast(postList.isLast())
                .isFirst(postList.isFirst())
                .totalPage(postList.getTotalPages())
                .totalElements(postList.getTotalElements())
                .listSize(postPreviewDtoList.size())
                .postList(postPreviewDtoList)
                .build();
    }
}
