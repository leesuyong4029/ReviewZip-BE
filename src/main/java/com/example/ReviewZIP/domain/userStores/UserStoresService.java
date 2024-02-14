package com.example.ReviewZIP.domain.userStores;


import com.example.ReviewZIP.domain.store.Stores;
import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
import com.example.ReviewZIP.domain.userStores.dto.request.UserStoresRequestDto;
import com.example.ReviewZIP.domain.userStores.dto.response.UserStoresResponseDto;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
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
    public void createUserStores(UserStoresRequestDto.CreateUserStoresDto dto) {

        // 임의로 유저 아이디 값에 1L을 지정
        Users user = usersRepository.findById(1L).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));

        UserStores userStores = UserStoresConverter.toEntity(dto);
        userStores.setUser(user);

        userStoresRepository.save(userStores);

    }

    public UserStoresResponseDto.StoreInfoListDto getStoreInfo(Long userId) {
        Users user = usersRepository.findById(userId).orElseThrow(()->new UsersHandler(ErrorStatus.USER_NOT_FOUND));
        List<UserStores> userStoresList = userStoresRepository.findAllByUser(user);

        Integer storeSavedNum = userStoresList.size();
        String nickname = user.getNickname();

        return UserStoresConverter.toStoreInfoListDto(userStoresList,storeSavedNum,nickname);
    }

    public Boolean isInterestPlace (Double lat, Double lon) {
        return userStoresRepository.existsUserStoresByLatitudeAndLongitude(lat,lon);
    }

    @Transactional
    public void deleteUserStores(Long userStoreId) {
        Users user = usersRepository.findById(1L).orElseThrow(()-> new UsersHandler(ErrorStatus.USER_NOT_FOUND));
        userStoresRepository.deleteByIdAndUser(userStoreId, user);
    }




}
