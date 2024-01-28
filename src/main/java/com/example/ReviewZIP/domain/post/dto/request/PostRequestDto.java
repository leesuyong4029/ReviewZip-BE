package com.example.ReviewZIP.domain.post.dto.request;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostRequestDto {
    private Long userId;
    private String comment;
    private Double point;
    private List<Long> imageIds = new ArrayList<>();
}