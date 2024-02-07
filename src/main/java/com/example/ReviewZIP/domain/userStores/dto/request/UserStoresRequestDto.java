package com.example.ReviewZIP.domain.userStores.dto.request;

import lombok.Getter;

public class UserStoresRequestDto {

    @Getter
    public static class CreateUserStoresDto {
        private String name;
        private String address;
        private Double latitude;
        private Double longitude;
    }
}
