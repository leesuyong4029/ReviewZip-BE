package com.example.ReviewZIP.domain.token.dto.request;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequestDto {

    private String email;
    private String phoneNum;
    private String name;
    private String nickname;
    private String password;
}
