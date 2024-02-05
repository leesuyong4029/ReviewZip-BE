package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.Follows;
import com.example.ReviewZIP.domain.follow.FollowsRepository;
import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.post.PostsRepository;
import com.example.ReviewZIP.domain.scrab.Scrabs;
import com.example.ReviewZIP.domain.scrab.ScrabsRepository;
import com.example.ReviewZIP.domain.searchHistory.SearchHistories;
import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.UsersHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersService {
    private final UsersRepository usersRepository;
    private final FollowsRepository followsRepository;
    private final PostsRepository postsRepository;
    private final ScrabsRepository scrabsRepository;

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
        List<Follows> followingList = followsRepository.findAllBySender(me);
        List<Long> followingIdList = new ArrayList<>();

        for (Follows following : followingList){
            Long followingId = following.getReceiver().getId();
            followingIdList.add(followingId);
        }
        return followingIdList;
    }

    public List<Follows> getFollowingList(Long userId){
        Users sender = usersRepository.findById(userId).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));
        List<Follows> FollowsPage = followsRepository.findAllBySender(sender);

        return FollowsPage;
    }

    public List<Follows> getFollowerList(Long userId){
        Users receiver = usersRepository.findById(userId).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));
        List<Follows> FollowsPage = followsRepository.findAllByReceiver(receiver);

        return FollowsPage;
    }

    public Page<Posts> getPostList(Long userId, Integer page, Integer size){
        Users user = usersRepository.findById(userId).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));
        Page<Posts> UserPage = postsRepository.findAllByUser(user, PageRequest.of(page, size));

        return UserPage;
    }

    public Page<Scrabs> getScrabList(Long userId, Integer page, Integer size){
        Users user = usersRepository.findById(userId).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));
        Page<Scrabs> UserPage = scrabsRepository.findAllByUser(user, PageRequest.of(page, size));

        return UserPage;
    }

    // 해당 유저가 맞는지에 대한 검증 필요, 원래 1L 필요하나 일단 데이터베이스 확인을 위하여 다음과 같이 진행
    @Transactional
    public void deleteUser(Long userId) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new UsersHandler(ErrorStatus.USER_NOT_FOUND));

        usersRepository.deleteById(userId);
    }

    public UserResponseDto.UserInfoDto getOtherInfo(Long userId){
        // 사용자 임의 처리, 1L 가정
        Users me = usersRepository.getById(1L);
        Users other = usersRepository.findById(userId)
                .orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));

        Integer followingNum = followsRepository.countBySenderId(userId);
        Integer followerNum = followsRepository.countByReceiverId(userId);
        boolean isFollowing = followsRepository.existsBySenderAndReceiver(me, other);

        return UsersConverter.toOtherInfoDto(other, followingNum, followerNum, isFollowing);
    }

    public List<SearchHistories> getHistoryList(Long userId){
        Users me = usersRepository.getById(userId);
        List<SearchHistories> historyList =  me.getSearchHistoriesList();
        Collections.reverse(historyList);
        return historyList;
    }
}
