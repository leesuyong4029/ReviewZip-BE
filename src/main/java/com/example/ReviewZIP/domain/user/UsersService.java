package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.user.dto.response.DeleteUserResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersService {
    private final UsersRepository usersRepository;

    @Transactional
    public void deleteUser(Long userId){
        Users user = usersRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("NOT FOUND USER"));


        usersRepository.deleteById(userId);

        // 추후 응답으로 반환 예정
    }
}
