package qp.official.qp.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import qp.official.qp.domain.User;
import qp.official.qp.service.TokenService.TokenService;
import qp.official.qp.service.UserService.UserService;

import java.util.Map;

@RestController
@AllArgsConstructor
public class TokenController {
    private final UserService userService;
    private final TokenService tokenService;
    /**
     * JWT 토큰 생성
     * @return jwt
     */
    @Operation(summary = "JWT 토큰 생성", description = "JWT와 userId반환")
    @PostMapping("/createJWT")
    public Map<String, Long> createJWT() {
        User user = userService.createTestUser();
        String jwt = tokenService.generateJWT(user.getUserId());
        Map<String, Long> map = Map.of(jwt, user.getUserId());
        return map;
    }

    /**
     * JWT 토큰 검증
     * @return void
     */
    @Operation(summary = "JWT 토큰 검증", description = "Header Authorization에 JWT 토큰을 넣어서 요청. JWT 토큰이 유효하면 true, 유효하지 않으면 false를 반환")
    @GetMapping("/validJWT")
    public boolean isValidJWT() {
        String token = tokenService.getJWT();
        User user = userService.getUserInfo(17L); // 테스트 용
        boolean value = tokenService.isValidToken(token, user.getUserId());
        return value;
    }

    /**
     * JWT 토큰 가져오기
     * @return void
     */
    @Operation(summary = "JWT 토큰 가져오기", description = "Header Authorization에 있는 토큰 가져오기")
    @GetMapping("/getToken")
    public String getJWToken() {
        String token = tokenService.getJWT();
        return token;
    }

    /**
     * refreshToken 생성하기
     * @return refreshToken
     */
    @Operation(summary = "refreshToken 생성하기", description = "refreshToken 생성하기. refreshToken과 userId 반환")
    @PostMapping("/createRefreshToken")
    public Map<String, Long> createRefreshToken() {
        User user = userService.createTestUser();
        String refreshToken = tokenService.generateRefreshToken(user.getUserId());
        Map<String, Long> map = Map.of(refreshToken, user.getUserId());
        return map;
    }

    /**
     * userId를 이용해 refreshToken 가져오기
     * @param userId
     * @return JWT 토큰 사용자 ID
     */
    @Operation(summary = "refreshToken을 이용해 JWT 갱신", description = "userId를 통해 refreshToken의 유효성을 검사하고 JWT 갱신. 갱신된 JWT 반환")
    @GetMapping("/getRefreshToken/{userId}")
    public String renewJWT(@PathVariable Long userId) {
        String refreshToken = tokenService.getRefreshToken(userId);
        // refresh Token이 유효한지 확인
        String newJWT = null;
        if(tokenService.isExpiredRefreshToken(refreshToken)) {
            // jwt 생성
            newJWT = tokenService.renewJWT(refreshToken);
        }
        return newJWT;
    }
}
