package com.example.ReviewZIP.domain.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StoreRequestDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoreInfoDto {
        private String name;
        private String addressName;
        private String roadAddressName;
        private String longitude;
        private String latitude;
    }
}
