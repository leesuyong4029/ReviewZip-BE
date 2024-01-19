package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.image.Images;
import com.example.ReviewZIP.domain.image.ImagesRepository;
import com.example.ReviewZIP.domain.post.dto.request.PostRequestDto;
import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.ImagesHandler;
import com.example.ReviewZIP.global.response.exception.handler.UsersHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostsService {
    private final ImagesRepository imagesRepository;
    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;

    @Transactional
    public Posts createPost(PostRequestDto postRequestDto) {
        Users user = usersRepository.findById(postRequestDto.getUserId()).orElseThrow(() -> new UsersHandler(ErrorStatus.USER_NOT_FOUND));

        Posts newPost = new Posts();
        newPost.setUser(user);
        newPost.setComment(postRequestDto.getComment());
        newPost.setPoint(postRequestDto.getPoint());
        newPost.setIs_read(false);

        Posts savedPost = postsRepository.save(newPost);

        for (Long imageId : postRequestDto.getImageIds()) {
            Images image = imagesRepository.findById(imageId).orElseThrow(() -> new ImagesHandler(ErrorStatus.IMAGE_NOT_FOUND));
            image.setPost(savedPost);
            image.setUser(user);
            imagesRepository.save(image);
            savedPost.getPostImageList().add(image); // Post 엔티티가 Images 엔티티의 변경 사항을 반영
        }
        return savedPost;
    }
}