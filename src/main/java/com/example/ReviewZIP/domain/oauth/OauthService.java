package com.example.ReviewZIP.domain.oauth;

import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OauthService {
    private final UsersRepository usersRepository;
    public boolean existBySocialId(Long id){
        return usersRepository.existsByUser_id(id);
    }

    public Users createUser(Long id, String nickname, String email){
        Users newUser = Users.builder()
                .user_id(id)
                .nickname(nickname)
                .name(nickname)
                .email(email)
                .build();
        return usersRepository.save(newUser);
    }
}
