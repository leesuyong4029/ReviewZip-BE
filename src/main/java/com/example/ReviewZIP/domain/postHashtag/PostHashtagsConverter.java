package com.example.ReviewZIP.domain.postHashtag;

import com.example.ReviewZIP.domain.postHashtag.dto.response.PostHashtagResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class PostHashtagsConverter {

    public static PostHashtagResponseDto.PostHashtagsPreviewDto toPostHashtagsPreviewDto(PostHashtags postHashtags) {
        return PostHashtagResponseDto.PostHashtagsPreviewDto.builder()
                .id(postHashtags.getId())
                .name(postHashtags.getHashtag())
                .build();

    }

    public static PostHashtagResponseDto.PostHashtagsPreviewListDto toPostHashtagsPreviewListDto(List<PostHashtags> postHashtagsList){

        List<PostHashtagResponseDto.PostHashtagsPreviewDto> postHashtagsPreviewDto = postHashtagsList.stream()
                .map(PostHashtagsConverter::toPostHashtagsPreviewDto)
                .collect(Collectors.toList());
        return PostHashtagResponseDto.PostHashtagsPreviewListDto.builder()
                .hashtagList(postHashtagsPreviewDto)
                .build();
    }
}