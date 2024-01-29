package com.example.ReviewZIP.domain.oauth;

import com.example.ReviewZIP.domain.jwt.TokenProvider;
import com.example.ReviewZIP.domain.oauth.dto.request.OauthRequestDto;
import com.example.ReviewZIP.domain.user.Status;
import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.GeneralHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OauthService {
    private final UsersRepository usersRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public Long createUser(String id, String nickname, String email){
        Users newUser = Users.builder()
                .social(id)
                .nickname(nickname)
                .name(nickname)
                .email(email)
                .status(Status.ENABLED)
                .build();
        usersRepository.save(newUser);

        return newUser.getId();
    }

    @Transactional
    public Map<String, Object> generateAccessToken(String id, String nickname, String email){
        boolean exist = usersRepository.existsBySocial(id);

        System.out.println(exist);
        Long userId;

        if(!exist) {
            // 유저가 존재하지 않을때 -> 유저 생성
            userId = createUser(id, nickname, email);
        } else {
            // 존재할 경우 해당 유저의 userId 반환
            userId = usersRepository.getBySocial(id).getId();
        }

        String accessToken = tokenProvider.makeToken(userId);

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("accessToken", accessToken);

        return map;
    }

    public List<String> getKakaoUserInfo(OauthRequestDto.kakaoTokenRequestDto request) throws JsonProcessingException {
        String token = request.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(null, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.POST,
                    kakaoProfileRequest,
                    String.class
            );
        } catch (Exception e){
            throw new GeneralHandler(ErrorStatus.KAKAO_TOKEN_ERROR);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = response.getBody();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String id = jsonNode.get("id").asText();
        String nickname = jsonNode.get("properties").get("nickname").asText();
        String email = jsonNode.get("kakao_account").get("email").asText();

        List<String> kakaoUserInfoList = Arrays.asList(id, nickname, email);

        return kakaoUserInfoList;
    }
}
