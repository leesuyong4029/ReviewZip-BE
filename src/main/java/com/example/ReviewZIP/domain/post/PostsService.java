package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.postHashtag.PostHashtags;
import com.example.ReviewZIP.domain.postHashtag.PostHashtagsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostHashtagsRepository postHashtagsRepository;

    public Page<Posts> searchPostByHashtag (Long id, Integer page){
        Page<PostHashtags> postHashtagsList = postHashtagsRepository.findPostHashtagsById(id, PageRequest.of(page,10));

        List<Posts> postsList = postHashtagsList.getContent().stream()
                .map(PostHashtags::getPost)
                .collect(Collectors.toList());

        return new PageImpl<>(postsList, postHashtagsList.getPageable(), postHashtagsList.getTotalElements());

    }
}
