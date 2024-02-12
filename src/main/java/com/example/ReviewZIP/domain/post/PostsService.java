package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.follow.Follows;
import com.example.ReviewZIP.domain.image.Images;
import com.example.ReviewZIP.domain.image.ImagesRepository;
import com.example.ReviewZIP.domain.post.dto.request.PostRequestDto;
import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;
import com.example.ReviewZIP.domain.postHashtag.PostHashtags;
import com.example.ReviewZIP.domain.postHashtag.PostHashtagsRepository;
import com.example.ReviewZIP.domain.postLike.PostLikes;
import com.example.ReviewZIP.domain.postLike.PostLikesRepository;
import com.example.ReviewZIP.domain.scrab.Scrabs;
import com.example.ReviewZIP.domain.scrab.ScrabsRepository;
import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
import com.example.ReviewZIP.global.redis.RedisService;
import com.example.ReviewZIP.global.response.ApiResponse;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.code.resultCode.SuccessStatus;
import com.example.ReviewZIP.global.response.exception.handler.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
    private final RedisService redisService;

    public List<PostHashtags> searchPostByHashtag (Long hashtagId){
        PostHashtags postHashtags = postHashtagsRepository.findById(hashtagId).orElseThrow(()->new PostHashtagsHandler(ErrorStatus.HASHTAG_NOT_FOUND));
        String tagName = postHashtags.getHashtag();
        return postHashtagsRepository.findAllByHashtag(tagName);
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
            savedPost.getPostImageList().add(image);
        }
        return savedPost;
    }

    public static final int NUM_OF_RANDOM_POST = 1;
    public PostResponseDto.PostInfoDto getOneRandomPostInfoDto(Long userId) {
        Users user = usersRepository.getById(1L);

        long totalPostCount = postsRepository.countByUserNot(user);

        if (totalPostCount < NUM_OF_RANDOM_POST) {
            throw new PostsHandler(ErrorStatus.NON_USER_POST_REQUIRED);
        }

        int randomIndex = (int)(Math.random() * totalPostCount);

        Page<Posts> postPage = postsRepository
                .findAllByUserNot(
                        user,
                        PageRequest.of(randomIndex, 1)
                );

        if (postPage.hasContent()) {
            Posts post = postPage.getContent().get(0);
            boolean checkLike = postLikesRepository.existsByUserAndPost(user, post);
            boolean checkScrab = scrabsRepository.existsByUserAndPost(user, post);
            String createdAt = getCreatedAt(post.getCreatedAt());

            return PostsConverter.toPostInfoResultDto(post, user, checkLike, checkScrab, createdAt);
        }
        throw new PostsHandler(ErrorStatus.NON_USER_POST_REQUIRED);
    }

    public static final int NUM_OF_RANDOM_POSTS = 3;
    public List<PostResponseDto.PostInfoDto> getThreeRandomPostsInfo(Long userId) {
        Users user = usersRepository.getById(1L);

        long totalPostCount  = postsRepository.countByUserNot(user);

        if (totalPostCount < NUM_OF_RANDOM_POSTS) {
            throw new PostsHandler(ErrorStatus.NON_USER_POST_REQUIRED);
        }

        Set<Integer> randomIndices = new HashSet<>();
        while (randomIndices.size() < NUM_OF_RANDOM_POSTS) {
            int idx = (int)(Math.random() * totalPostCount);
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
                String createdAt = getCreatedAt(post.getCreatedAt());

                randomPostInfoDtos.add(PostsConverter.toPostInfoResultDto(post, user, checkLike, checkScrab, createdAt));
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

        String createdAt = getCreatedAt(post.getCreatedAt());

        return PostsConverter.toPostInfoResultDto(post, user, checkLike, checkScrab, createdAt);
    }

    List<PostResponseDto.PostInfoDto> getPostInfoDtoList(List<PostHashtags> postHashtagList){
        return postHashtagList.stream()
                .map(postHashtag -> getPostInfoDto(postHashtag.getPost().getId()))
                .collect(Collectors.toList());
    }

    public String getCreatedAt(LocalDateTime createdAt){

        // 서버시간을 UTC로 설정
        ZoneId serverZone = ZoneId.of("UTC");
        LocalDateTime now = ZonedDateTime.now(serverZone).toLocalDateTime();

        Duration duration = Duration.between(createdAt, now);

        long seconds = duration.getSeconds();
        long minutes = duration.toMinutes();
        long hours = duration.toHours();

        if (minutes < 1){
            return seconds + "초 전";
        } else if (minutes < 60){
            return minutes + "분 전";
        } else if (hours < 24){
            return hours + "시간 전";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
            return createdAt.format(formatter);
        }
    }

    @Transactional
    public void deletePost(Long postId){
        Posts post = postsRepository.findById(postId).orElseThrow(()-> new PostsHandler(ErrorStatus.POST_NOT_FOUND));

        postsRepository.deleteById(post.getId());

    }

    @Transactional
    public void removeLike(Long postId, Long userId) {
        PostLikes postLikes = postLikesRepository.findByPostIdAndUserId(postId, userId).orElseThrow(() -> new PostLikesHandler(ErrorStatus.POSTLIKE_NOT_FOUND));
        postLikesRepository.delete(postLikes);
    }
    @Transactional
    public ApiResponse<SuccessStatus> addLike(Long postId) {
        // user 1L로 대체
        Users user = usersRepository.getById(1L);
        Posts post = postsRepository.findById(postId).orElseThrow(() -> new PostLikesHandler(ErrorStatus.POST_NOT_FOUND));
        boolean alreadyLike = postLikesRepository.existsByUserAndPost(user, post);
        if(alreadyLike){
            return ApiResponse.onSuccess(SuccessStatus._OK);
        }
        PostLikes postLikes = PostLikes.builder()
                .post(post)
                .user(user)
                .build();

        postLikesRepository.save(postLikes);
        try {
            return ApiResponse.onSuccess(SuccessStatus._OK);
        } catch (Exception e) {
            throw new PostLikesHandler(ErrorStatus.POSTLIKE_CREATE_FAIL);
        }

    }


    public List<Long> getFollowigIdList(){
        // 일단 1L로 나를 대체
        Users me = usersRepository.getById(1L);
        List<Follows> followingList = me.getFollowingList();
        List<Long> followingIdList = new ArrayList<>();

        for (Follows following : followingList){
            Long followingId = following.getReceiver().getId();
            followingIdList.add(followingId);
        }
        return followingIdList;
    }

    public List<Users> getPostLikeUserList(Long postId){
        return  postLikesRepository.findUsersByPostId(postId);
    }

    @Transactional
    public ApiResponse<SuccessStatus> createScrabs(Long postId){
        // userId는 1로 대체
        Users me = usersRepository.getById(1L);
        Posts post = postsRepository.findById(postId).orElseThrow(()->new PostsHandler(ErrorStatus.POST_NOT_FOUND));
        boolean alreadyScrab = scrabsRepository.existsByUserAndPost(me, post);
        if(alreadyScrab){
            return ApiResponse.onSuccess(SuccessStatus._OK);
        }
        Scrabs newScrab = Scrabs.builder()
                .user(me)
                .post(post)
                .build();

        scrabsRepository.save(newScrab);
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }

    @Transactional
    public void deleteScrabs(Long postId){
        // userId는 1로 대체
        Users me = usersRepository.getById(1L);
        Posts post = postsRepository.findById(postId).orElseThrow(()->new PostsHandler(ErrorStatus.POST_NOT_FOUND));

        Scrabs scrabs = scrabsRepository.findByUserAndPost(me, post);

        scrabsRepository.delete(scrabs);
    }

    @Transactional
    public void addHashtags(String query, Long postId) {
        redisService.addHashtag(query);

        PostHashtags postHashtags = PostHashtags.builder()
                .hashtag(query)
                .post(postsRepository.findById(postId).orElseThrow( () -> new PostsHandler(ErrorStatus.POST_NOT_FOUND)))
                .build();
        postHashtagsRepository.save(postHashtags);
    }
}
