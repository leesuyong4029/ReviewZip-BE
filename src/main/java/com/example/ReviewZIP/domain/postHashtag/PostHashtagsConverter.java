package com.example.ReviewZIP.domain.postHashtag;

import com.example.ReviewZIP.domain.postHashtag.dto.response.PostHashtagResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostHashtagsConverter {

    public static PostHashtagsRepository postHashtagsRepository;

    public PostHashtagsConverter(PostHashtagsRepository postHashtagsRepository) {
        this.postHashtagsRepository = postHashtagsRepository;
    }


    public static PostHashtagResponseDto.PostHashtagsPreviewDto toPostHashtagsPreviewDto(PostHashtags postHashtags, int postNum) {
        return PostHashtagResponseDto.PostHashtagsPreviewDto.builder()
                .hashtagId(postHashtags.getId())
                .tagName(postHashtags.getHashtag())
                .postNum(postNum)
                .build();

    }

    public static List<PostHashtagResponseDto.PostHashtagsPreviewDto> toPostHashtagsPreviewListDto(List<PostHashtags> postHashtagsList){
        return postHashtagsList.stream()
                .map(postHashtags -> {
                        int postNum = postHashtagsRepository.countPostByHashtag(postHashtags.getHashtag());
                        return toPostHashtagsPreviewDto(postHashtags, postNum);})
                .collect(Collectors.toList());
    }


}