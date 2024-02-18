package com.example.ReviewZIP.domain.userStores;

import com.example.ReviewZIP.domain.userStores.dto.request.UserStoresRequestDto;
import com.example.ReviewZIP.domain.userStores.dto.response.UserStoresResponseDto;

import java.util.ArrayList;
import java.util.List;

public class UserStoresConverter {

    public static UserStores toEntity(UserStoresRequestDto.CreateUserStoresDto request) {
        return UserStores.builder()
                .name(request.getName())
                .road_address_name(request.getRoadAddressName())
                .address_name(request.getAddressName())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();
    }

    public static UserStoresResponseDto.StoreInfoDto toStoreInfoDto(UserStores userStores) {
        return UserStoresResponseDto.StoreInfoDto.builder()
                .storeId(userStores.getId())
                .latitude(userStores.getLatitude())
                .longitude(userStores.getLongitude())
                .place(userStores.getName())
                .roadAddressName(userStores.getRoad_address_name())
                .addressName(userStores.getAddress_name())
                .build();
    }

    public static UserStoresResponseDto.StoreInfoListDto toStoreInfoListDto(List<UserStores> userStoresList, Integer saveNum, String nickname) {
        List<UserStoresResponseDto.StoreInfoDto> storeInfoDtoList = new ArrayList<>();

        for(UserStores userStores : userStoresList) {
            storeInfoDtoList.add(toStoreInfoDto(userStores));
        }

        return UserStoresResponseDto.StoreInfoListDto.builder()
                .storeInfoDtoList(storeInfoDtoList)
                .savedStoresCount(saveNum)
                .nickname(nickname)
                .build();
    }
}
