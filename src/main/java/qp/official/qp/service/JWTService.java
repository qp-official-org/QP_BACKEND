package qp.official.qp.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import qp.official.qp.domain.User;
import qp.official.qp.repository.UserRepository;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class JWTService {

    private final String secretKey;
    private final SecretKey key;

    @Autowired
    public JWTService(@Value("${JWT_SECRET}") String secretKey) {
        this.secretKey = secretKey;
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public static final int REFRESH_TOKEN_EXPIRED_TIME = 60 * 60 * 24 * 14; // 14 days

    @Autowired
    private UserRepository userRepository;

    // Generate JWT Token
    public String generateJWT(Long userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12L))
                .signWith(key)
                .compact();// payload
    }

    // Get JWT Token
    public String getJWT() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String token = request.getHeader("Authorization");

        System.out.println("token = " + token);

        return token;
    }

    // JWT Token 을 이용해 userId 가져오기
    public Long getUserIdFromJWT() {
        String accessToken = getJWT();
        if (accessToken == null || accessToken.isEmpty()) {
            return null;
        }
        Jws<Claims> jws;
        try {
            jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken);
        } catch (JwtException e) {
            throw new RuntimeException(e);
        }
        return jws.getBody()
                .get("userId", Long.class);
    }

    // check JWT validation
    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            System.out.println("claimsJws: " + claimsJws);

            Date expirationDate = claimsJws.getBody().getExpiration();
            if(expirationDate.before(new Date())) {
                System.out.println("토큰 expired");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("토큰 파싱 실패" + e.getMessage());
            return false;
        }

    }

    // JWT Token 을 이용해 가져온 userId와 로그인한 userId가 일치하는지 확인
    public boolean isSameUserId(Long userIdFromJWT, Long userId) {
        if (userIdFromJWT == null) {
            return false;
        }
        return userIdFromJWT.equals(userId);
    }


    // Generate Refresh Token
    public String generateRefreshToken(Long userId) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRED_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // get Refresh Token
    public Map<String, LocalDateTime> getRefreshToken(Long userId) {
        User user = userRepository.findByUserId(userId);
        String refreshToken = user.getRefreshToken();
        Map<String, LocalDateTime> map = Map.of(user.getRefreshToken(), user.getRefreshTokenExpiresAt());
        return map;
    }

    // refresh Token 만료 기간 확인
    public boolean isExpired(LocalDateTime refreshTokenExpiresAt) {
        System.out.println("refreshTokenExpiresAt: " + refreshTokenExpiresAt);
        System.out.println("LocalDateTime.now(): " + LocalDateTime.now());
        if(refreshTokenExpiresAt.isAfter(LocalDateTime.now())) {
            return true;
        }
        return false;
    }
}
