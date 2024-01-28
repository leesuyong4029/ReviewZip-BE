package com.example.ReviewZIP.domain.image.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ImageRequestDto {
    private Long userId;
    private List<String> urlList;
}
