package com.example.ReviewZIP.domain.image.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadImageResponseDto {
    public List<Long> imageIds;
}
