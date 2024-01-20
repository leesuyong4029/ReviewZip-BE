package com.example.ReviewZIP.domain.image.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ImageRequestDto {
    private Long userId;
    private List<String> urlList;
}
