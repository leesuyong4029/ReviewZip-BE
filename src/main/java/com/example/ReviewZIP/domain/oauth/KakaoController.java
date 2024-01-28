package com.example.ReviewZIP.domain.oauth;

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
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao")
public class KakaoController {

    private final OauthService oauthService;
    //토큰 이용해서 유저 정보 가져오기
    @GetMapping()
    public Long kakaoLogin(@RequestBody OauthRequestDto.kakaoTokenRequest request) throws JsonProcessingException {
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
        JsonNode  jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties").get("nickname").asText();
        String email = jsonNode.get("kakao_account").get("email").asText();

        //이제 id로 유저정보가 있는지 없는지 확인
        boolean exist = oauthService.existBySocialId(id);

        //유저가 존재할 때
        if(exist == true){

        }else{
            // 유저가 존재하지 않을때 -> 유저 생성
            Users newUser = oauthService.createUser(id, nickname, email);

            oauthService.createUser(id,nickname, email);
        }

    }
}
