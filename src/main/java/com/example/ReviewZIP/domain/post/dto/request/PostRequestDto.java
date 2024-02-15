package com.example.ReviewZIP.domain.post.dto.request;

import com.example.ReviewZIP.domain.store.dto.request.StoreRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class PostRequestDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatedPostRequestDto {
        private String comment;
        private Double point;
        private List<Long> imageIds = new ArrayList<>();
        private StoreRequestDto.StoreInfoDto storeInfo;
    }
}