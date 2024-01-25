package qp.official.qp.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import qp.official.qp.domain.User;
import qp.official.qp.service.JWTService;
import qp.official.qp.service.UserService;

@RestController
@AllArgsConstructor
public class TokenController {
    private UserService userService;
    private JWTService jwtService;

    /**
     * JWT 토큰 생성
     * @return jwt
     */
    @PostMapping("/createJWT")
    public String createJWT() {
        User user = userService.createTestUser();
        System.out.println("user.getUserId(): " + user.getUserId());
        String jwt = jwtService.createJWT(user.getUserId());
        return jwt;
    }

    /**
     * JWT 토큰 검증
     * @return void
     */
    @GetMapping("/validateJWT")
    public void validateJWT() {
        String token = jwtService.getJWT();
        boolean value = jwtService.isValidToken(token);
        System.out.println("value: " + value);

    }

    /**
     * JWT 토큰 가져오기
     * @return void
     */
    @GetMapping("/getToken")
    public void getToken() {
        String token = jwtService.getJWT();
        System.out.println("token: " + token);
    }

    /**
     * JWT 토큰을 이용해 userId 가져오기
     * 로그인한 사용자와 토큰의 사용자가 일치하는지 확인
     * @param userId
     * @return JWT 토큰 사용자 ID
     */
    @GetMapping("/getUserId/{userId}")
    public Long getUserId(@PathVariable Long userId) {
        Long userIdFrmJWT = jwtService.getUserId();
        System.out.println("userIdFrmJWT: " + userIdFrmJWT);
        if(!userId.equals(userIdFrmJWT))
            throw new RuntimeException("JWT Token is not valid with Jwt : " + userIdFrmJWT + " userId : " + userId);

        return userIdFrmJWT;
    }

    /**
     * refreshToken 생성하기
     * @return refreshToken
     */
    @PostMapping("/createRefreshToken")
    public String createRefreshToken() {
        User user = userService.createTestUser();
        String refreshToken = jwtService.createRefreshToken(user.getUserId());
        System.out.println("user.getUserId(): " + user.getUserId());
        System.out.println("refresh token: " + refreshToken);
        return refreshToken;
    }

//    @PostMapping("/refresh")
//    public ResponseEntity<Map<String, String>> refresh(@RequestBody TokenRequest request) {
//        System.out.println("refresh token: " + request.getRefresh_token());
//        return ResponseEntity.ok(userService.refresh(request.getRefresh_token()));
//    }
}
