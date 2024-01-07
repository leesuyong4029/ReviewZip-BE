package com.example.ReviewZIP.domain.user.service;

import com.example.ReviewZIP.domain.user.converter.MyPageConverter;
import com.example.ReviewZIP.domain.user.dto.response.GetMyPageResDto;
import com.example.ReviewZIP.domain.user.entity.Users;
import com.example.ReviewZIP.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor // final or @NonMull이 붙은 필드에 대해 생성자를 자동 생성
@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final MyPageConverter myPageConverter;

    // id로 유저 정보 가져오기
    public GetMyPageResDto getMyPageById(Long id){
        Users user = usersRepository.findUsersById(id);
        return myPageConverter.convert(user);
    }
}
