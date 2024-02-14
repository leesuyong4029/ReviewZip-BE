package com.example.ReviewZIP.domain.searchHistory;

import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.SearchHandler;
import com.example.ReviewZIP.global.response.exception.handler.UsersHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchHistoriesService {
    private final UsersRepository usersRepository;
    private final SearchHistoriesRepository searchHistoriesRepository;

    @Transactional
    public void saveUserSearchHistory(Long userId, Long objectId){
        Users me = usersRepository.getById(userId);
        Users object = usersRepository.findById(objectId).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));

        SearchHistories userHistory = SearchHistories.builder()
                .type(SearchType.USER)
                .user(me)
                .object(object)
                .hashtag(null)
                .build();

        searchHistoriesRepository.save(userHistory);
    }

    @Transactional
    public void saveHashtagSearchHistory(Long userId, String hashtag){
        Users me = usersRepository.getById(userId);
        SearchHistories hashtagHistory = SearchHistories.builder()
                .type(SearchType.HASHTAG)
                .user(me)
                .object(null)
                .hashtag(hashtag)
                .build();

        searchHistoriesRepository.save(hashtagHistory);
    }

    @Transactional
    public void deleteUserSearchHistory(Long userId, Long historyId) {
        SearchHistories history = searchHistoriesRepository.findByIdAndUserId(historyId, userId).orElseThrow(()-> new SearchHandler(ErrorStatus.HISTORY_NOT_FOUND));
        searchHistoriesRepository.delete(history);
    }
}
