package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.user.dto.response.OtherInfoResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UsersController {
    private final UsersService usersService;
    @GetMapping("/{userId}")
    public ResponseEntity<OtherInfoResDto> getOtherInfo(@PathVariable(name = "userId") Long userId){

        return ResponseEntity.ok(usersService.getOtherInfo(userId));
    }
}
