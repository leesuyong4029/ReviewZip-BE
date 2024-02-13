package com.example.ReviewZIP.domain.token;

import com.example.ReviewZIP.domain.token.dto.request.KakaoRequestDto;
import com.example.ReviewZIP.domain.token.dto.request.LoginRequestDto;
import com.example.ReviewZIP.domain.token.dto.request.SignUpRequestDto;
import com.example.ReviewZIP.domain.token.dto.response.SignUpResponseDto;
import com.example.ReviewZIP.domain.token.dto.response.TokenDto;
import com.example.ReviewZIP.domain.user.Status;
import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
import com.example.ReviewZIP.global.jwt.JwtProvider;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.handler.UsersHandler;
import com.example.ReviewZIP.global.security.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        if(usersRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new UsersHandler(ErrorStatus.USER_EXISTS_EMAIL);
        }

        signUpRequestDto.setPassword(encodePassword(signUpRequestDto.getPassword()));

        return SignUpResponseDto.signUpResponseDto(usersRepository.save(Users.toEntity(signUpRequestDto)));
    }

    public String encodePassword(String password) {

        return bCryptPasswordEncoder.encode(password);
    }

    @Transactional
    public TokenDto login(LoginRequestDto loginRequestDto) {
        // 1. Login ID/PW를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        // 2. authentication이 실행이 될 때, UserDetailsServiceImpl 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. UserDetailsImpl에서 직접 userId 가져오기
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getUserId();

        // 4. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = jwtProvider.generateToken(authentication, userId.toString());
        // 5. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        return tokenDto;
    }

    @Transactional
    public Long createUser(String id, String nickname, String email){
        Users newUser = Users.builder()
                .social(id)
                .nickname(nickname)
                .name(nickname)
                .email(email)
                .status(Status.ENABLED)
                .profileUrl("https://reviewzipbucket.s3.ap-northeast-2.amazonaws.com/ReviewImage/911a02f0-206c-4fb0-b287-f49b58429526.png")
                .build();
        usersRepository.save(newUser);

        return newUser.getId();
    }

    public List<String> getKakaoUserInfo(KakaoRequestDto request) throws JsonProcessingException {
        String token = request.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(null, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
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

    @Transactional
    public TokenDto kakaoLogin(List<String> kakaoUserInfo) {
        Long userIdd = null;

        boolean exists = usersRepository.existsBySocial(kakaoUserInfo.get(0));
        Optional<Users> user = null;
        if(!exists){
            userIdd = createUser(kakaoUserInfo.get(0), kakaoUserInfo.get(1), kakaoUserInfo.get(2));
            user = usersRepository.findById(userIdd);
        } else{
            user = usersRepository.findBySocial(kakaoUserInfo.get(0));
            userIdd = user.get().getId();
        }

        //UserDetailsImpl userDetail = new UserDetailsImpl(user.get());

        TokenDto tokenDto = jwtProvider.generateKakaoToken(userIdd.toString(), user.get().getEmail());

        RefreshToken refreshToken = RefreshToken.builder()
                .key(user.get().getEmail())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        return tokenDto;
    }
}
