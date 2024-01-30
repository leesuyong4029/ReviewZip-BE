package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.Follows;
import com.example.ReviewZIP.domain.follow.FollowsRepository;
import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.post.PostsRepository;
import com.example.ReviewZIP.domain.scrab.Scrabs;
import com.example.ReviewZIP.domain.scrab.ScrabsRepository;
import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.UsersHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Page<Users> findUsersByName(String name, Integer page) {
        Page<Users> pageUsers = usersRepository.findByName(name, PageRequest.of(page, 10));
        Users users = usersRepository.findById(1L).orElseThrow(() -> new UsersHandler(ErrorStatus.USER_NOT_FOUND));

     if (pageUsers.isEmpty()) {
        throw new UsersHandler(ErrorStatus.USER_NOT_FOUND);
    }

    List<Follows> followsList = users.getFollowingList();

    List<Users> followingUsersList = followsList.stream()
            .filter(follow -> follow.getReceiver() != null)
            .map(Follows::getReceiver)
            .collect(Collectors.toList());

    List<Users> filteredUsersList = pageUsers.getContent().stream()
            .filter(user -> !followingUsersList.contains(user))
            .collect(Collectors.toList());

        return new PageImpl<>(filteredUsersList, pageUsers.getPageable(), pageUsers.getTotalElements());
}
    public Page<Users> findUsersByNickname(String nickname, Integer page, Integer size) throws UsersHandler {
        Page<Users> pageUsers = usersRepository.findByNickname(nickname, PageRequest.of(page, size));
        Users users = usersRepository.findById(1L).orElseThrow(() -> new UsersHandler(ErrorStatus.USER_NOT_FOUND));

        if (pageUsers.isEmpty()) {
            throw new UsersHandler(ErrorStatus.USER_NOT_FOUND);
        }

        List<Follows> followsList = users.getFollowingList();

        List<Users> followingUsersList = followsList.stream()
                .filter(follow -> follow.getReceiver() != null)
                .map(Follows::getReceiver)
                .collect(Collectors.toList());

        List<Users> filteredUsersList = pageUsers.getContent().stream()
                .filter(user -> !followingUsersList.contains(user))
                .collect(Collectors.toList());

        return new PageImpl<>(filteredUsersList, pageUsers.getPageable(), pageUsers.getTotalElements());

    }

    public Page<Follows> getFollowingList(Long userId, Integer page, Integer size){
        Users sender = usersRepository.findById(userId).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));
        Page<Follows> FollowsPage = followsRepository.findAllBySender(sender, PageRequest.of(page, size));

        return FollowsPage;
    }

    public Page<Follows> getFollowerList(Long userId, Integer page, Integer size){
        Users receiver = usersRepository.findById(userId).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));
        Page<Follows> FollowsPage = followsRepository.findAllByReceiver(receiver, PageRequest.of(page, size));

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

}
