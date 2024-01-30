package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.image.Images;
import com.example.ReviewZIP.domain.image.ImagesRepository;
import com.example.ReviewZIP.domain.post.dto.request.PostRequestDto;
import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;
import com.example.ReviewZIP.domain.postHashtag.PostHashtags;
import com.example.ReviewZIP.domain.postHashtag.PostHashtagsRepository;
import com.example.ReviewZIP.domain.postLike.PostLikesRepository;
import com.example.ReviewZIP.domain.scrab.ScrabsRepository;
import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.ImagesHandler;
import com.example.ReviewZIP.global.response.exception.handler.PostHashtagsHandler;
import com.example.ReviewZIP.global.response.exception.handler.PostsHandler;
import com.example.ReviewZIP.global.response.exception.handler.UsersHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostsService {
    private final ImagesRepository imagesRepository;
    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;
    private final PostLikesRepository postLikesRepository;
    private final ScrabsRepository scrabsRepository;
    private final PostHashtagsRepository postHashtagsRepository;

    public Page<Posts> searchPostByHashtag (Long id, Integer page, Integer size){
        Page<PostHashtags> postHashtagsList = postHashtagsRepository.findPostHashtagsById(id, PageRequest.of(page,size));

        if (postHashtagsList.isEmpty()) {
            throw new PostHashtagsHandler(ErrorStatus.HASHTAG_NOT_FOUND);
        }
        List<Posts> postsList = postHashtagsList.getContent().stream()
                .map(PostHashtags::getPost)
                .collect(Collectors.toList());

        return new PageImpl<>(postsList, postHashtagsList.getPageable(), postHashtagsList.getTotalElements());

    }
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

    public List<PostResponseDto.PostInfoDto> getRandomPostInfoDto(Long userId) {
        Users user = usersRepository.getById(userId);

        long qty = postsRepository.countByUserNot(user);

        if (qty < 3) {
            throw new PostsHandler(ErrorStatus.POST_RANDOM_FAIL);
        }

        Set<Integer> randomIndices = new HashSet<>();
        while (randomIndices.size() < 3) {
            int idx = (int)(Math.random() * qty);
            randomIndices.add(idx);
        }

        List<PostResponseDto.PostInfoDto> randomPostInfoDtos = new ArrayList<>();
        for (Integer idx : randomIndices) {
            Page<Posts> postPage = postsRepository
                    .findAllByUserNot(
                            user,
                            PageRequest.of(idx, 1)
                    );

            if (postPage.hasContent()) {
                Posts post = postPage.getContent().get(0);
                boolean checkLike = postLikesRepository.existsByUserAndPost(user, post);
                boolean checkScrab = scrabsRepository.existsByUserAndPost(user, post);
                randomPostInfoDtos.add(PostsConverter.toPostInfoResultDto(post, checkLike, checkScrab));
            }
        }

        return randomPostInfoDtos;
    }

    // 특정 게시물의 정보 가져오기
    public PostResponseDto.PostInfoDto getPostInfoDto(Long postId){
        // 좋아요와 스크랩 표시를 위하여 1L로 해당 유저를 대체
        Users user = usersRepository.getById(1L);
        Posts post = postsRepository.findById(postId).orElseThrow(()->new PostsHandler(ErrorStatus.POST_NOT_FOUND));
        boolean checkLike = postLikesRepository.existsByUserAndPost(user, post);
        boolean checkScrab = scrabsRepository.existsByUserAndPost(user, post);

        return PostsConverter.toPostInfoResultDto(post, checkLike, checkScrab);
    }

    @Transactional
    public void deletePost(Long postId){
        Posts post = postsRepository.findById(postId).orElseThrow(()-> new PostsHandler(ErrorStatus.POST_NOT_FOUND));

        postsRepository.deleteById(post.getId());

    }
}
