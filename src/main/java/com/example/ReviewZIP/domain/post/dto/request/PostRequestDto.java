package com.example.ReviewZIP.domain.post.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostRequestDto {
    private Long userId;
    private String comment;
    private Double point;
    private List<Long> imageIds = new ArrayList<>();
}