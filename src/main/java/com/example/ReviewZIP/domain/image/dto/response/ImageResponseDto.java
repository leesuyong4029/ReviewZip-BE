package com.example.ReviewZIP.domain.image.dto.response;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponseDto {
    private List<Long> imageIds;
}
