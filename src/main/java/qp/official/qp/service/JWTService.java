package qp.official.qp.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
public class JWTService {
//    public static String JWT_SECRET;
//
//    @Value("${external.jwt.secret}")
//    public void setKey(String key) {
//        JWT_SECRET = key;
//    }




//    @Value("${jwt.secret}")
//    @Getter
//    private String secretKey;

//    public String getSecretKey() {
//        return secretKey;
//    }

    @Value("${JWT_SECRET}")
    private String secretKey;
    private SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    public static final int REFRESH_TOKEN_EXPIRED_TIME = 60 * 60 * 24 * 14; // 14 days

    // Create JWT Token
    public String createJWT(Long userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24L))
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
    public Long getUserId() {
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

    // Create Refresh Token
    public String createRefreshToken(Long userId) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRED_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // check token validation
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

    public static String generateRefreshToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
