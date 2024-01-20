package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import com.example.ReviewZIP.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UsersController {

    private final UsersService userService;

    @GetMapping("/search/nickname")
    public ApiResponse<UserResponseDto.UserListDto> searchUsersByNickname(@RequestParam String nickname, @RequestParam (defaultValue = "0") Integer page) {
        Page<Users> userPage = userService.findUsersByNickname(nickname, page);
        UserResponseDto.UserListDto userListDto = UsersConverter.toUserListDto(userPage);
        return ApiResponse.onSuccess(userListDto);
    }
}
