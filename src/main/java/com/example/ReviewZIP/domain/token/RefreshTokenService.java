package com.example.ReviewZIP.domain.token;

import com.example.ReviewZIP.domain.token.dto.request.LoginRequestDto;
import com.example.ReviewZIP.domain.token.dto.request.SignUpRequestDto;
import com.example.ReviewZIP.domain.token.dto.response.SignUpResponseDto;
import com.example.ReviewZIP.domain.token.dto.response.TokenDto;
import com.example.ReviewZIP.domain.user.Users;
import com.example.ReviewZIP.domain.user.UsersRepository;
import com.example.ReviewZIP.global.jwt.JwtProvider;
import com.example.ReviewZIP.global.response.code.resultCode.ErrorStatus;
import com.example.ReviewZIP.global.response.exception.GeneralException;
import com.example.ReviewZIP.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new GeneralException(ErrorStatus.USER_EXISTS_EMAIL);
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

}
