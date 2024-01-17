package com.example.ReviewZIP.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository userRepository;

    public Page<Users> findUsersByName(String name, Integer page) {
        return userRepository.findByName(name, PageRequest.of(page, 10));
    }
}
