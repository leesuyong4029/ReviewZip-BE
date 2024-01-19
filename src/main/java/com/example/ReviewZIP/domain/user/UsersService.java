package com.example.ReviewZIP.domain.user;

import com.example.ReviewZIP.domain.image.Images;
import com.example.ReviewZIP.domain.image.ImagesRepository;
import com.example.ReviewZIP.domain.post.Posts;
import com.example.ReviewZIP.domain.post.PostsRepository;
import com.example.ReviewZIP.domain.postHashtag.PostHashtags;
import com.example.ReviewZIP.domain.postHashtag.PostHashtagsRepository;
import com.example.ReviewZIP.domain.postLike.PostLikes;
import com.example.ReviewZIP.domain.postLike.PostLikesRepository;
import com.example.ReviewZIP.domain.scrab.Scrabs;
import com.example.ReviewZIP.domain.scrab.ScrabsRepository;
import com.example.ReviewZIP.global.response.code.BaseErrorCode;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.UsersHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersService {
    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;
    private final ScrabsRepository scrabsRepository;
    private final ImagesRepository imagesRepository;
    private final PostLikesRepository postLikesRepository;
    private final PostHashtagsRepository postHashtagsRepository;

    @Transactional
    public void deleteUser(Long userId){
        Users user = usersRepository.findById(userId).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));

        try {
            postLikesRepository.deleteById(userId);

            for (Scrabs scrab : user.getScrabList()) {
                scrabsRepository.delete(scrab);
            }

            for (Posts post : user.getPostList()) {
                for (Images image : post.getPostImageList()) {
                    imagesRepository.delete(image);
                }

                for (PostLikes postLike : post.getPostLikeList()) {
                    postLikesRepository.delete(postLike);
                }

                for (PostHashtags postHashtag : post.getPostHashtagList()) {
                    postHashtagsRepository.delete(postHashtag);
                }
                postsRepository.delete(post);
            }

            usersRepository.deleteById(userId);
        } catch (Exception e){
            throw new UsersHandler(ErrorStatus.USER_DELETE_FAIL);
        }
    }
}
