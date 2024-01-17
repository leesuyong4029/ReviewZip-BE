package com.example.ReviewZIP.domain.postLike;



import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersConverter;
import com.example.ReviewZIP.domain.user.dto.response.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostLikesController {

    private final PostLikesService postLikesService;


    @GetMapping("/{postId}/users")
    public ResponseEntity<UserResponseDTO.UserListDTO> getUsersByPostIdFromPostLikes(@PathVariable Long postId, @RequestParam (defaultValue = "0") Integer page) {
        Page<Users> pageUsers = postLikesService.getUsersByPostId(postId, page);
        UserResponseDTO.UserListDTO userListDTO = UsersConverter.convertToUserListDTO(pageUsers);
        return ResponseEntity.ok(userListDTO);
    }

}
