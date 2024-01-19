package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UsersController {
    private final UsersService usersService;

    @DeleteMapping("/{userId}")
    public ApiResponse<Void> deleteUser(@PathVariable(name = "userId")Long userId) {
        return ApiResponse.onFailure("USER410", "유저 삭제에 실패하였습니다.", null);
    }
}
