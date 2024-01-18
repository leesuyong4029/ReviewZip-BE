package com.example.ReviewZIP.domain.image.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ImagesRequestDto {
    @NotNull(message = "Name cannot be null")
    private String name;
    private String type;
    private List<MultipartFile> fileList;
}
