package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.Follows;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.UsersHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository userRepository;

    public Page<Users> findUsersByNickname(String nickname, Integer page) throws UsersHandler {
        Page<Users> pageUsers = userRepository.findByNickname(nickname, PageRequest.of(page, 10));
        Users users = userRepository.findById(1L).orElseThrow(() -> new UsersHandler(ErrorStatus.USER_NOT_FOUND));

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
}
