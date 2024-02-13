package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.Follows;
import com.example.ReviewZIP.domain.follow.FollowsRepository;
import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.post.PostsConverter;
import com.example.ReviewZIP.domain.post.PostsRepository;
import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;
import com.example.ReviewZIP.domain.postLike.PostLikesRepository;
import com.example.ReviewZIP.domain.scrab.Scrabs;
import com.example.ReviewZIP.domain.scrab.ScrabsRepository;
import com.example.ReviewZIP.domain.searchHistory.SearchHistories;
import com.example.ReviewZIP.domain.user.dto.request.UserRequestDto;
import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.PostsHandler;
import com.example.ReviewZIP.global.response.exception.handler.UsersHandler;
import com.example.ReviewZIP.global.s3.S3Service;
import com.example.ReviewZIP.global.s3.dto.S3Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersService {
    private final UsersRepository usersRepository;
    private final FollowsRepository followsRepository;
    private final PostsRepository postsRepository;
    private final ScrabsRepository scrabsRepository;
    private final PostLikesRepository postLikesRepository;
    private final S3Service s3Service;

    public List<Users> findUsersByName(String name) {
        List<Users> pageUsers = usersRepository.findByName(name);

        return pageUsers;
}
    public List<Users> findUsersByNickname(String nickname) throws UsersHandler {
        List<Users> pageUsers = usersRepository.findByNickname(nickname);

        return pageUsers;

    }

    public List<Long> getFollowigIdList(Long userId){
        // 일단 1L로 나를 대체
        Users me = usersRepository.getById(userId);
        List<Follows> followingList = me.getFollowingList();
        List<Long> followingIdList = new ArrayList<>();

        for (Follows following : followingList){
            Long followingId = following.getReceiver().getId();
            followingIdList.add(followingId);
        }
        return followingIdList;
    }

    public List<Follows> getFollowingList(Long userId){
        Users user = usersRepository.findById(userId).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));
        return user.getFollowingList();
    }

    public List<Follows> getFollowerList(Long userId){
        Users user = usersRepository.findById(userId).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));
        return user.getFollowerList();
    }

    @Transactional
    public void deleteUser(Long userId) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new UsersHandler(ErrorStatus.USER_NOT_FOUND));

        usersRepository.deleteById(userId);
    }

    public UserResponseDto.UserInfoDto getUserInfo(String email){

        return UsersConverter.toUserInfoDto(usersRepository.findByEmail(email).orElseThrow(() -> new UsersHandler(ErrorStatus.USER_NOT_FOUND)));
    }

    public UserRequestDto.UserProfileUrlDto updateProfileUrl(Long userId, UserRequestDto.UserProfileUrlDto userProfileUrlDto){
        Users user = usersRepository.findById(userId).orElseThrow(() -> new UsersHandler(ErrorStatus.USER_NOT_FOUND));
        user.setProfileUrl(userProfileUrlDto.getProfileUrl());
        usersRepository.save(user);

        return userProfileUrlDto;
    }

    public UserRequestDto.UserNicknameDto updateUserNickname(Long userId, UserRequestDto.UserNicknameDto userNicknameDto){
        Users user = usersRepository.findById(userId).orElseThrow(() -> new UsersHandler(ErrorStatus.USER_NOT_FOUND));
        user.setNickname(userNicknameDto.getNickname());
        usersRepository.save(user);

        return userNicknameDto;
    }

    public UserResponseDto.OtherUserInfoDto getOtherInfo(Long userId){
        // 사용자 임의 처리, 1L 가정
        Users me = usersRepository.getById(userId);
        Users other = usersRepository.findById(userId)
                .orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));

        boolean isFollowing = followsRepository.existsBySenderAndReceiver(me, other);

        return UsersConverter.toOtherInfoDto(other,isFollowing);
    }

    public List<SearchHistories> getHistoryList(Long userId){
        Users me = usersRepository.getById(userId);
        List<SearchHistories> historyList =  me.getSearchHistoriesList();
        Collections.reverse(historyList);
        return historyList;
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

    public PostResponseDto.PostInfoDto getPostInfoDto(Long postId, Long userId){
        Users user = usersRepository.getById(userId);
        Posts post = postsRepository.findById(postId).orElseThrow(()->new PostsHandler(ErrorStatus.POST_NOT_FOUND));
        boolean checkLike = postLikesRepository.existsByUserAndPost(user, post);
        boolean checkScrab = scrabsRepository.existsByUserAndPost(user, post);

        String createdAt = getCreatedAt(post.getCreatedAt());

        return PostsConverter.toPostInfoResultDto(post, user, checkLike, checkScrab, createdAt);
    }

    List<PostResponseDto.PostInfoDto> getPostInfoDtoList(Long userId, List<Posts> postList){
        List<PostResponseDto.PostInfoDto> postInfoDtoList = postList.stream()
                .map(post -> getPostInfoDto(post.getId(), userId))
                .collect(Collectors.toList());
        Collections.reverse(postInfoDtoList);
        return postInfoDtoList;
    }

    List<PostResponseDto.PostInfoDto> getScrabInfoDtoList(Long userId, List<Scrabs> scrabList){
        List<PostResponseDto.PostInfoDto> scrabInfoDtoList = scrabList.stream()
                .map(scrab -> getPostInfoDto(scrab.getPost().getId(), userId))
                .collect(Collectors.toList());
        Collections.reverse(scrabInfoDtoList);
        return scrabInfoDtoList;
    }


    List<Posts> getPostList(Long userId){
        Users me = usersRepository.getById(userId);
        return me.getPostList();
    }

    List<Scrabs> getScrabList(Long userId){
        Users me = usersRepository.getById(userId);
        return me.getScrabList();
    }
}
