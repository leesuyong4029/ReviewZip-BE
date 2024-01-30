package com.example.ReviewZIP.domain.image;

import com.example.ReviewZIP.domain.image.dto.response.UploadImageResponseDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/images")
public class ImagesController {
    private final ImagesService imageService;

    @PostMapping("/users/{userId}")
    @Operation(summary = "이미지 업로드 API", description = "UploadImageResponseDto 사용")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SEARCH203",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE402", description = "이미지 업로드 실패",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<UploadImageResponseDto> uploadImage(@PathVariable(name="userId") Long userId, @RequestParam("fileList") List<MultipartFile> fileList){
        List<Images> imageList = imageService.uploadImage(fileList, userId);
        UploadImageResponseDto imageResponseDto = ImageConverter.toUploadImageDto(imageList);
        return ApiResponse.onSuccess(imageResponseDto);
    }
}
