package com.example.ReviewZIP.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository userRepository;

    public Page<Users> findUsersByNickname(String nickname, Integer page) {
        return userRepository.findByNickname(nickname, PageRequest.of(page, 10));
    }
}
