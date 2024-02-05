package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.follow.Follows;
import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.scrab.Scrabs;
import com.example.ReviewZIP.domain.searchHistory.SearchHistories;
import com.example.ReviewZIP.domain.searchHistory.SearchType;
import com.example.ReviewZIP.domain.user.dto.response.FollowResponseDto;
import com.example.ReviewZIP.domain.user.dto.response.UserResponseDto;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.SearchHandler;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UsersConverter {
    public static UserResponseDto.UserPreviewDto toUserPreviewDto(Users user, List<Long> followingIdList) {
        boolean following = followingIdList.contains(user.getId());
        return UserResponseDto.UserPreviewDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .profileImages(user.getProfileUrl())
                .following(following)
                .build();
    }

    public static List<UserResponseDto.UserPreviewDto> toUserPreviewListDto(List<Users> userList, List<Long> followingIdList) {
        return userList.stream()
                .map(user -> toUserPreviewDto(user, followingIdList))
                .collect(Collectors.toList());


    }

    // 팔로잉 목록 converter
    public static FollowResponseDto.FollowingPreviewDto toFollowingPreviewDto(Follows follows){
        return FollowResponseDto.FollowingPreviewDto.builder()
                .followingId(follows.getReceiver().getId())
                .profileUrl(follows.getReceiver().getProfileUrl())
                .nickname(follows.getReceiver().getNickname())
                .build();
    }

    public static List<FollowResponseDto.FollowingPreviewDto> toFollowingPreviewListDto(List<Follows> followsList){
        return followsList.stream()
                .map(UsersConverter::toFollowingPreviewDto).collect(Collectors.toList());

    }

    public static FollowResponseDto.FollowerPreviewDto toFollowerPreviewDto(Follows follows){
        return FollowResponseDto.FollowerPreviewDto.builder()
                .followerId(follows.getSender().getId())
                .nickname(follows.getSender().getNickname())
                .profileUrl(follows.getReceiver().getProfileUrl())
                .build();
    }

    public static List<FollowResponseDto.FollowerPreviewDto> toFollowerPreviewListDto(List<Follows> followsList){
        return followsList.stream()
                .map(UsersConverter::toFollowerPreviewDto).collect(Collectors.toList());

    }

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

    public static UserResponseDto.PostPreviewDto toScrabPreviewDto(Scrabs scrabs){
        return UserResponseDto.PostPreviewDto.builder()
                .postId(scrabs.getPost().getId())
                .likeNum(scrabs.getPost().getPostLikeList().size())
                .scrabNum(scrabs.getPost().getScrabList().size())
                .postImageUrl(scrabs.getPost().getPostImageList().get(0).getUrl())
                .build();
    }

    public static UserResponseDto.PostPreviewListDto toScrabPreviewListDto(Page<Scrabs> scrabList) {
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

    public static UserResponseDto.UserInfoDto toOtherInfoDto(Users user, Integer followingNum, Integer followerNum, boolean isFollowing){

        String imageUrl = (user.getProfileUrl() != null) ? user.getProfileUrl() : null;

        return UserResponseDto.UserInfoDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .profileUrl(imageUrl)
                .followingNum(followingNum)
                .followerNum(followerNum)
                .isFollowing(isFollowing)
                .build();
    }


    public static  UserResponseDto.HistoryDto toHistoryDto(SearchHistories history, List<Long> followingIdList){
        if(history.getType().equals(SearchType.USER)){
            return UserResponseDto.HistoryDto.builder()
                    .historyId(history.getId())
                    .user(toUserPreviewDto(history.getObject(), followingIdList))
                    .hashtag(null)
                    .type("USER")
                    .build();

        } else if (history.getType().equals(SearchType.HASHTAG)){
            return UserResponseDto.HistoryDto.builder()
                    .historyId(history.getId())
                    .user(null)
                    .hashtag(history.getHashtag())
                    .type("HASHTAG")
                    .build();
        } else{
            throw new SearchHandler(ErrorStatus.HISTORY_TYPE_NOT_VALID);
        }
    }

    public static List<UserResponseDto.HistoryDto> toHistoryDtoList(List<SearchHistories> historyList, List<Long> followingIdList){
        return historyList.stream()
                .map(history->toHistoryDto(history, followingIdList))
                .collect(Collectors.toList());
    }
}
