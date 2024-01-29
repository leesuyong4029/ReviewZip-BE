package com.example.ReviewZIP.domain.oauth;

import com.example.ReviewZIP.domain.jwt.TokenProvider;
import com.example.ReviewZIP.domain.oauth.dto.request.OauthRequestDto;
import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OauthService {
    private final UsersRepository usersRepository;
    private final TokenProvider tokenProvider;

    public Users createUser(String id, String nickname, String email){
        Users newUser = Users.builder()
                .social(id)
                .nickname(nickname)
                .name(nickname)
                .email(email)
                .build();

        return usersRepository.save(newUser);
    }

    public Map<String, Object> generateAccessToken(String id, String nickname, String email){
        boolean exist = usersRepository.existsBySocial(id);

        // 유저가 존재하지 않을때 -> 유저 생성
        if(!exist) {
            Users newUser = createUser(id, nickname, email);
        }

        // 생성된 유저의 id값을 이용하여 token화
        Long userId = usersRepository.getBySocial(id).getId();

        String accessToken = tokenProvider.makeToken(userId);

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("accessToken", accessToken);

        return map;
    }

    public List<String> getKakaoUserInfo(OauthRequestDto.kakaoTokenRequest request) throws JsonProcessingException {
        String token = request.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", " application/x-www-form-urlencoded;charset=utf-8 ");
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kako.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

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
