package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.Follows;
import com.example.ReviewZIP.domain.user.dto.response.FollowResponseDto;
import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.scrab.Scrabs;
import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UsersConverter {
    public static UserResponseDto.UserPreviewDto toUserPreviewDto(Users user) {
        return UserResponseDto.UserPreviewDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .profileImage(user.getProfileUrl())
                .build();
    }

    public static UserResponseDto.UserPreviewListDto toUserListDto(Page<Users> userList) {
        List<UserResponseDto.UserPreviewDto> userDtoList = userList.stream()
                .map(UsersConverter::toUserPreviewDto)
                .collect(Collectors.toList());

        return UserResponseDto.UserPreviewListDto.builder()
                .isLast(userList.isLast())
                .isFirst(userList.isFirst())
                .totalPage(userList.getTotalPages())
                .totalElements(userList.getTotalElements())
                .listSize(userDtoList.size())
                .userList(userDtoList)
                .build();
    }

    // 팔로잉 목록 converter
    public static FollowResponseDto.FollowingPreviewDto toFollowingPreviewDto(Follows follows){
        return FollowResponseDto.FollowingPreviewDto.builder()
                .followingId(follows.getReceiver().getId())
                .profileUrl(follows.getReceiver().getProfileUrl())
                .nickname(follows.getReceiver().getNickname())
                .build();
    }

    public static FollowResponseDto.FollowingPreviewListDto toFollowingPreviewListDto(Page<Follows> followsList){
        List<FollowResponseDto.FollowingPreviewDto> followingPreviewDtoList = followsList.stream()
                .map(UsersConverter::toFollowingPreviewDto).collect(Collectors.toList());

        return FollowResponseDto.FollowingPreviewListDto.builder()
                .isLast(followsList.isLast())
                .isFirst(followsList.isFirst())
                .totalElements(followsList.getTotalElements())
                .totalPage(followsList.getTotalPages())
                .listSize(followingPreviewDtoList.size())
                .followsList(followingPreviewDtoList)
                .build();
    }

    // 팔로워 목록 converter
    public static FollowResponseDto.FollowerPreviewDto toFollowerPreviewDto(Follows follows){
        return FollowResponseDto.FollowerPreviewDto.builder()
                .followerId(follows.getSender().getId())
                .nickname(follows.getSender().getNickname())
                .profileUrl(follows.getReceiver().getProfileUrl())
                .build();
    }

    public static FollowResponseDto.FollowerPreviewListDto toFollowerPreviewListDto(Page<Follows> followsList){
        List<FollowResponseDto.FollowerPreviewDto> followerPreviewDtoList = followsList.stream()
                .map(UsersConverter::toFollowerPreviewDto).collect(Collectors.toList());

        return FollowResponseDto.FollowerPreviewListDto.builder()
                .isLast(followsList.isLast())
                .isFirst(followsList.isFirst())
                .totalElements(followsList.getTotalElements())
                .totalPage(followsList.getTotalPages())
                .listSize(followerPreviewDtoList.size())
                .followsList(followerPreviewDtoList)
                .build();
    }

    // 게시글 미리보기 converter
    public static UserResponseDto.PostPreviewDto toPostPreviewDto(Posts post){
        return UserResponseDto.PostPreviewDto.builder()
                .postId(post.getId())
                .likeNum(post.getPostLikeList().size())
                .scrabNum(post.getScrabList().size())
                .postImageUrl(post.getPostImageList().get(0).getUrl())
                .build();
    }

    public static UserResponseDto.PostPreviewListDto toPostPreviewListDto(Page<Posts> postList){
        List<UserResponseDto.PostPreviewDto> userPostPriviewDtoList = postList.stream()
                .map(UsersConverter::toPostPreviewDto).collect(Collectors.toList());

        return UserResponseDto.PostPreviewListDto.builder()
                .isLast(postList.isLast())
                .isFirst(postList.isFirst())
                .totalElements(postList.getTotalElements())
                .totalPage(postList.getTotalPages())
                .listSize(userPostPriviewDtoList.size())
                .postList(userPostPriviewDtoList)
                .build();
    }

    // scrab한 게시글 미리보기 converter
    public static UserResponseDto.PostPreviewDto toScrabPreviewDto(Scrabs scrabs){
        return UserResponseDto.PostPreviewDto.builder()
                .postId(scrabs.getPost().getId())
                .likeNum(scrabs.getPost().getPostLikeList().size())
                .scrabNum(scrabs.getPost().getScrabList().size())
                .postImageUrl(scrabs.getPost().getPostImageList().get(0).getUrl())
                .build();
    }

    public static UserResponseDto.PostPreviewListDto toScrabPreviewListDto(Page<Scrabs> scrabList){
        List<UserResponseDto.PostPreviewDto> scrabPriviewDtoList = scrabList.stream()
                .map(UsersConverter::toScrabPreviewDto).collect(Collectors.toList());

        return UserResponseDto.PostPreviewListDto.builder()
                .isLast(scrabList.isLast())
                .isFirst(scrabList.isFirst())
                .totalElements(scrabList.getTotalElements())
                .totalPage(scrabList.getTotalPages())
                .listSize(scrabPriviewDtoList.size())
                .postList(scrabPriviewDtoList)
                .build();
    }
}
