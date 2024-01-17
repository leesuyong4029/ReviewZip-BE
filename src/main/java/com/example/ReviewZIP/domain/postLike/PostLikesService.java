package com.example.ReviewZIP.domain.postLike;

import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.post.PostsRepository;
import com.example.ReviewZIP.domain.postLike.dto.request.PostLikesRequestDTO;
import com.example.ReviewZIP.domain.postLike.dto.response.PostLikesResponseDTO;
import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikesService {

    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;

    public PostLikesResponseDTO.PostLikesResultDTO addLike(PostLikesRequestDTO.PostLikesDTO postLikesDTO) {
        Users user = usersRepository.findById(postLikesDTO.getUserId()).orElseThrow();
        Posts post = postsRepository.findById(postLikesDTO.getPostId()).orElseThrow();

        PostLikes postLikes = new PostLikes();
        postLikes.setPost(post);
        postLikes.setUser(user);

        return PostLikesConverter.convertToPostLikesDTO(postLikes);
    }
}
