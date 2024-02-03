package com.example.ReviewZIP.domain.postHashtag.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class PostHashtagResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostHashtagsPreviewDto {
        private Long id;
        private String name;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostHashtagsPreviewListDto {
        List<PostHashtagsPreviewDto> hashtagList;
    }


}
