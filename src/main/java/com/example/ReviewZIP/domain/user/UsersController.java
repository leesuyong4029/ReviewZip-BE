package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.user.dto.response.UserResponseDTO;
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
    public ResponseEntity<UserResponseDTO.UserListDTO> searchUsersByNickname(@RequestParam String nickname, @RequestParam (defaultValue = "0") Integer page) {
        Page<Users> userPage = userService.findUsersByNickname(nickname, page);
        UserResponseDTO.UserListDTO userListDTO = UsersConverter.toUserListDTO(userPage);
        return ResponseEntity.ok(userListDTO);
    }
}
