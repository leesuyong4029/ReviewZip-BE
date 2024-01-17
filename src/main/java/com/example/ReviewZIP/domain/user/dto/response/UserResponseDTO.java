package com.example.ReviewZIP.domain.user.dto.response;

import com.example.ReviewZIP.domain.user.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class UserResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserListDTO {
        List<UserDTO> userList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDTO {
        @NotNull
        private Long id;

        @NotBlank
        private String email;

        @NotBlank
        private String name;

        @NotBlank
        private String user_id;

        @NotBlank
        private String password;

        @NotBlank
        private String phone_num;

        @NotBlank
        private String nickname;
        private Status status;

        private LocalDate createdAt;
        private LocalDate updatedAt;
    }
}
