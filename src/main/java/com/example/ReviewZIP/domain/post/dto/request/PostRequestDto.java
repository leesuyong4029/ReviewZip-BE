package com.example.ReviewZIP.domain.post.dto.request;

import com.example.ReviewZIP.domain.image.dto.response.ImageResponseDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostRequestDto {
    private Long userId;
    private String comment;
    private Double point;
    private List<ImageResponseDto.ImageDto> imageList = new ArrayList<>();
}