package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.follow.FollowsRepository;
import com.example.ReviewZIP.domain.image.Images;
import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;
import com.example.ReviewZIP.domain.postHashtag.PostHashtags;
import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostsConverter {

    public static UsersRepository usersRepository;

    public static FollowsRepository followsRepository;

    public PostsConverter(UsersRepository usersRepository, FollowsRepository followsRepository) {
        this.usersRepository = usersRepository;
        this.followsRepository = followsRepository;
    }


    public static PostResponseDto.PostPreviewDto toPostPreviewDto(Posts post) {
        return PostResponseDto.PostPreviewDto.builder()
                .id(post.getId())
                .likeNum(post.getPostLikeList().size())
                .scrabNum(post.getScrabList().size())
                .imageUrl(post.getPostImageList().get(0).getUrl())
                .build();
    }

    public static PostResponseDto.PostPreviewListDto toPostPreviewListDto(Page<Posts> postList) {
        List<PostResponseDto.PostPreviewDto> postPreviewDtoList = postList.stream()
                .map(PostsConverter::toPostPreviewDto).collect(Collectors.toList());

        return PostResponseDto.PostPreviewListDto.builder()
                .isLast(postList.isLast())
                .isFirst(postList.isFirst())
                .totalPage(postList.getTotalPages())
                .totalElements(postList.getTotalElements())
                .listSize(postPreviewDtoList.size())
                .postList(postPreviewDtoList)
                .build();
    }
    public static PostResponseDto.CreatedPostResponseDto toPostResponseDto(Posts post) {
        List<Long> imageIds = post.getPostImageList().stream()
                .map(Images::getId)
                .collect(Collectors.toList());

        return PostResponseDto.CreatedPostResponseDto.builder()
                .postId(post.getId())
                .comment(post.getComment())
                .point(post.getPoint())
                .userId(post.getUser().getId())
                .imageIds(imageIds)
                .build();
    }
    public static PostResponseDto.UserInfoDto toUserInfoDto(Users user){
        return PostResponseDto.UserInfoDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .profileUrl(user.getProfileUrl())
                .build();
    }

    public static PostResponseDto.ImageListDto toImageListDto(Images image){
        return PostResponseDto.ImageListDto.builder()
                .id(image.getId())
                .url(image.getUrl())
                .build();
    }
    public static PostResponseDto.PostInfoDto toPostInfoResultDto(Posts post, boolean checkLike, boolean checkScrab){
        PostResponseDto.UserInfoDto userInfoDto = toUserInfoDto(post.getUser());

        List<PostResponseDto.ImageListDto> imageListDto = post.getPostImageList().stream()
                .map(PostsConverter::toImageListDto).collect(Collectors.toList());

        LocalDateTime createdAt = post.getCreatedAt() != null ? post.getCreatedAt() : null;

        return PostResponseDto.PostInfoDto.builder()
                .postId(post.getId())
                .comment(post.getComment())
                .point(post.getPoint())
                .likeNum(post.getPostLikeList().size())
                .checkLike(checkLike)
                .checkScrab(checkScrab)
                .hashtags(post.getPostHashtagList().stream().map(PostHashtags::getHashtag).collect(Collectors.toList()))
                .userInfo(userInfoDto)
                .postImages(imageListDto)
                .createdAt(createdAt)
                .build();
    }

    public static List<PostResponseDto.PostUserLikeDto> toPostUserLikeListDto(List<Users> userList, List<Long> likeAndFollowingIdList){
        List<PostResponseDto.PostUserLikeDto> postUserLikeDtoList = new ArrayList<>();
        for(Users user : userList){
            boolean isFollowing = likeAndFollowingIdList.contains(user.getId());
            PostResponseDto.PostUserLikeDto postUserLikeDto = PostResponseDto.PostUserLikeDto.builder()
                    .userId(user.getId())
                    .nickname(user.getNickname())
                    .profileUrl(user.getProfileUrl())
                    .isFollowing(isFollowing)
                    .build();
            postUserLikeDtoList.add(postUserLikeDto);
        }
        return postUserLikeDtoList;
    }
}
