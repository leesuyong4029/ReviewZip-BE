package com.example.ReviewZIP.domain.userStores.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UserStoresResponseDto {

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    public static class StoreInfoDto {
        private Long storeId;
        private Double latitude;
        private Double longitude;
        private String place;
        private String address;

    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    public static class StoreInfoListDto {
        List<StoreInfoDto> storeInfoDtoList;
        private Integer savedStoresCount;
        private String nickname;
    }
}
