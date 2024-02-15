package com.example.ReviewZIP.domain.userStores;


import com.example.ReviewZIP.domain.store.Stores;
import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
import com.example.ReviewZIP.domain.userStores.dto.request.UserStoresRequestDto;
import com.example.ReviewZIP.domain.userStores.dto.response.UserStoresResponseDto;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.UserStoresHandler;
import com.example.ReviewZIP.global.response.exception.handler.UsersHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserStoresService {

    private final UsersRepository usersRepository;
    private final UserStoresRepository userStoresRepository;
    @Transactional
    public void createUserStores(Long myId, UserStoresRequestDto.CreateUserStoresDto dto) {
        Users user = usersRepository.getById(myId);
        UserStores userStores = UserStoresConverter.toEntity(dto);
        userStores.setUser(user);

        try {
            userStoresRepository.save(userStores);
        } catch (Exception e) {
            throw new UserStoresHandler(ErrorStatus.USER_STORES_CREATE_FAIL);
        }

    }

    public UserStoresResponseDto.StoreInfoListDto getStoreInfo(Long userId) {
        Users user = usersRepository.findById(userId).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));
        List<UserStores> userStoresList = userStoresRepository.findAllByUser(user);

        Integer storeSavedNum = userStoresList.size();
        String nickname = user.getNickname();

        return UserStoresConverter.toStoreInfoListDto(userStoresList,storeSavedNum,nickname);
    }

    public UserStoresResponseDto.StoreInfoListDto getMyStoreInfo(Long myId) {
        Users user = usersRepository.getById(myId);
        List<UserStores> userStoresList = userStoresRepository.findAllByUser(user);

        Integer storeSavedNum = userStoresList.size();
        String nickname = user.getNickname();

        return UserStoresConverter.toStoreInfoListDto(userStoresList,storeSavedNum,nickname);
    }

    public Boolean isInterestPlace (Long myId, Double lat, Double lon) {
        Users user = usersRepository.getById(myId);
        return userStoresRepository.existsUserStoresByLatitudeAndLongitudeAndUser(lat,lon,user);
    }

    @Transactional
    public void deleteUserStores(Long myId, Long userStoreId) {
        Users user = usersRepository.getById(myId);
        try {
            userStoresRepository.deleteByIdAndUser(userStoreId, user);
        } catch (Exception e) {
            throw new UserStoresHandler(ErrorStatus.USER_STORES_DELETE_FAIL);
        }
    }




}
