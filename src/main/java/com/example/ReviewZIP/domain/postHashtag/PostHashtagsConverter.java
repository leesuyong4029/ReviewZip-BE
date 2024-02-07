package com.example.ReviewZIP.domain.postHashtag;

import com.example.ReviewZIP.domain.postHashtag.dto.response.PostHashtagResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class PostHashtagsConverter {

    public static PostHashtagResponseDto.PostHashtagsPreviewDto toPostHashtagsPreviewDto(PostHashtags postHashtags) {
        return PostHashtagResponseDto.PostHashtagsPreviewDto.builder()
                .hashtagId(postHashtags.getId())
                .tagName(postHashtags.getHashtag())
                .build();

    }

    public static List<PostHashtagResponseDto.PostHashtagsPreviewDto> toPostHashtagsPreviewListDto(List<PostHashtags> postHashtagsList){

        return postHashtagsList.stream()
                .map(PostHashtagsConverter::toPostHashtagsPreviewDto)
                .collect(Collectors.toList());
    }
}