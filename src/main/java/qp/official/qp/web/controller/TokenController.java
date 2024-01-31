package qp.official.qp.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.apiPayload.exception.handler.TokenHandler;
import qp.official.qp.domain.User;
import qp.official.qp.service.TokenService.TokenService;
import qp.official.qp.service.UserService;

import javax.validation.Valid;
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
    @Operation(summary = "JWT 토큰 검증", description = "Header access_token에 JWT 토큰을 넣어서 요청. JWT 토큰이 유효하면 true, 유효하지 않으면 false를 반환")
    @GetMapping("/validJWT")
    public boolean isValidJWT(@RequestParam @Valid Long userId) {
        String token = tokenService.getJWT();
        User user = userService.getUserInfo(userId);
        boolean value = tokenService.isValidToken(token, user.getUserId());
        return value;
    }

    /**
     * refreshToken 생성하기
     * @return refreshToken
     */
    @Operation(summary = "refreshToken 생성하기", description = "refreshToken 생성하기. refreshToken과 userId 반환")
    @PostMapping("/createRefreshToken")
    public Map<String, Long> createRefreshToken(@RequestParam @Valid Long userId) {
        String refreshToken = tokenService.generateRefreshToken(userId);
        Map<String, Long> map = Map.of(refreshToken, userId);
        return map;
    }

    /**
     * refreshToken을 이용하여 JWT 갱신
     * @param userId
     * @return 새로 갱신된 JWT 토큰
     */
    @Operation(summary = "refreshToken을 이용해 JWT 갱신", description = "userId를 통해 refreshToken의 유효성을 검사하고 jwt 반환. Header의 access_token 필요")
    @GetMapping("/renewToken/{userId}")
    public String renewJWToken(@PathVariable @Valid Long userId) {
        String refreshToken = tokenService.getRefreshToken(userId);
        // refresh Token이 유효한지 확인
        if(tokenService.isExpiredRefreshToken(refreshToken)) {
            // jwt 생성
            return tokenService.renewJWT(refreshToken);
        }
        throw new TokenHandler(ErrorStatus.TOKEN_EXPIRED);
    }

}
