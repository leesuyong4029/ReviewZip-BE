package com.example.ReviewZIP.domain.image;

import com.example.ReviewZIP.domain.image.dto.request.ImageRequestDto;
import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.ImagesHandler;
import com.example.ReviewZIP.global.response.exception.handler.UsersHandler;
import com.example.ReviewZIP.global.s3.S3Service;
import com.example.ReviewZIP.global.s3.dto.S3Result;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImagesService {
    private final ImagesRepository imagesRepository;
    private final UsersRepository usersRepository;
    private final S3Service s3Service;

    @Transactional
    public Images uploadImage(MultipartFile file, Long userId) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new UsersHandler(ErrorStatus.USER_NOT_FOUND));

        S3Result s3Result = s3Service.uploadFile(file);

        Images newImage = new Images();
        newImage.setName(file.getOriginalFilename());
        newImage.setUrl(s3Result.getFileUrl());
        newImage.setType(file.getContentType());
        newImage.setUser(user);

        return imagesRepository.save(newImage);
    }
}
