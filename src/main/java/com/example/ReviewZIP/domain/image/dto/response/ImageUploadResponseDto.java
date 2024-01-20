package com.example.ReviewZIP.domain.image.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageUploadResponseDto {
    public List<Long> imageIds;
}
