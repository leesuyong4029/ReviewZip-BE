package com.example.ReviewZIP.domain.post;

import com.example.ReviewZIP.domain.follow.FollowsRepository;
import com.example.ReviewZIP.domain.image.Images;
import com.example.ReviewZIP.domain.post.dto.request.PostRequestDto;
import com.example.ReviewZIP.domain.post.dto.response.PostResponseDto;
import com.example.ReviewZIP.domain.postHashtag.PostHashtags;
import com.example.ReviewZIP.domain.store.Stores;
import com.example.ReviewZIP.domain.store.dto.request.StoreRequestDto;
import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
import org.springframework.stereotype.Component;

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

    public static Posts toPostDto(PostRequestDto.CreatedPostRequestDto PostDto, Users user) {
        return Posts.builder()
                .comment(PostDto.getComment())
                .point(PostDto.getPoint())
                .is_read(false)
                .user(user)
                .postImageList(new ArrayList<>())
                .storeList(new ArrayList<>())
                .postHashtagList(new ArrayList<>())
                .postLikeList(new ArrayList<>())
                .scrabList(new ArrayList<>())
                .build();
    }

    public static Stores toStoreEntity(StoreRequestDto.StoreInfoDto storeInfoDto) {
        return Stores.builder()
                .name(storeInfoDto.getName())
                .address_name(storeInfoDto.getAddressName())
                .road_address_name(storeInfoDto.getRoadAddressName())
                .longitude(storeInfoDto.getLongitude())
                .latitude(storeInfoDto.getLatitude())
                .build();
    }

    public static PostResponseDto.UserInfoDto toUserInfoDto(Users user){
        return PostResponseDto.UserInfoDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .profileUrl(user.getProfileUrl())
                .build();
    }

    public static PostResponseDto.ImageDto toImageDto(Images image){
        return PostResponseDto.ImageDto.builder()
                .imageId(image.getId())
                .imageUrl(image.getUrl())
                .build();
    }

    public static PostResponseDto.HashtagDto toHashtagDto(PostHashtags postHashtags){
        return PostResponseDto.HashtagDto.builder()
                .hashtagId(postHashtags.getId())
                .tagName(postHashtags.getHashtag())
                .build();
    }

    public static PostResponseDto.PostInfoDto toPostInfoResultDto(Posts post, Users user, boolean checkLike, boolean checkScrab, String createdAt){
        PostResponseDto.UserInfoDto userInfoDto = toUserInfoDto(post.getUser());

        List<PostResponseDto.ImageDto> imageListDto = post.getPostImageList().stream()
                .map(PostsConverter::toImageDto).collect(Collectors.toList());

        List<PostResponseDto.HashtagDto> hashtagList = post.getPostHashtagList().stream()
                .map(PostsConverter::toHashtagDto).collect(Collectors.toList());

        boolean mine = false;
        if(user.getId().equals(post.getUser().getId())) {
            mine = true;
        }


        return PostResponseDto.PostInfoDto.builder()
                .postId(post.getId())
                .comment(post.getComment())
                .point(post.getPoint())
                .likeNum(post.getPostLikeList().size())
                .scrabNum(post.getScrabList().size())
                .hashtagNum(post.getPostHashtagList().size())
                .checkLike(checkLike)
                .checkScrab(checkScrab)
                .hashtags(hashtagList)
                .user(userInfoDto)
                .checkMine(mine)
                .postImages(imageListDto)
                .createdAt(createdAt)
                .build();
    }

    public static List<PostResponseDto.PostUserLikeDto> toPostUserLikeListDto(List<Users> postLikeUserList, List<Long> followingIdList){
        List<PostResponseDto.PostUserLikeDto> postUserLikeDtoList = new ArrayList<>();
        for(Users user : postLikeUserList){
            boolean isFollowing = followingIdList.contains(user.getId());
            PostResponseDto.PostUserLikeDto postUserLikeDto = PostResponseDto.PostUserLikeDto.builder()
                    .userId(user.getId())
                    .name(user.getName())
                    .nickname(user.getNickname())
                    .profileUrl(user.getProfileUrl())
                    .following(isFollowing)
                    .build();
            postUserLikeDtoList.add(postUserLikeDto);
        }
        
        return postUserLikeDtoList;
    }

    public static PostHashtags toPostHashtags(String hashtag, Posts posts){
        return PostHashtags.builder()
                .hashtag(hashtag)
                .post(posts)
                .build();
    }

}
