package com.example.ReviewZIP.domain.user.controller;

import com.example.ReviewZIP.domain.user.dto.response.GetMyPageResDto;
import com.example.ReviewZIP.domain.user.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }

    // 내 정보(마이페이지) 가져오기
    @GetMapping("me/{id}")
    public GetMyPageResDto getMyPageById(@PathVariable Long id){
        return usersService.getMyPageById(id);
    }
}
