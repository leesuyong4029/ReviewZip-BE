package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.UsersHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository userRepository;

    public Page<Users> findUsersByNickname(String nickname, Integer page) throws UsersHandler {
        Page<Users> pageUsers = userRepository.findByNickname(nickname, PageRequest.of(page, 10));

        if (pageUsers.isEmpty()) {
            throw new UsersHandler(ErrorStatus.USER_NOT_FOUND);
        }

        return pageUsers;
    }
}
