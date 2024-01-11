package com.example.ReviewZIP.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersService {
    private final UsersRepository usersRepository;

    @Transactional
    public void deleteUser(Long userId){
        usersRepository.deleteById(userId);
    }
}
