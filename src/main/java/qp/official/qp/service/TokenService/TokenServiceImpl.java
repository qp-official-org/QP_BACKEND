package qp.official.qp.service.TokenService;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import qp.official.qp.domain.User;
import qp.official.qp.repository.UserRepository;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService{

    private String secretKey;
    private SecretKey key;

    public TokenServiceImpl(@Value("${JWT_SECRET}") String secretKey) {
        this.secretKey = secretKey;
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public static final int JWT_EXPIRED_TIME = 60 * 60 * 2; // 2 hours
    public static final int REFRESH_TOKEN_EXPIRED_TIME = 60 * 60 * 24 * 14; // 14 days

    private UserRepository userRepository;

    // Generate JWT Token
    public String generateJWT(Long userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRED_TIME))
                .signWith(key)
                .compact();// payload
    }

    // Get JWT Token
    public String getJWT() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String token = request.getHeader("access_token"); // jwt
        System.out.println("token = " + token);
        return token;
    }

    // JWT Token 을 이용해 userId 가져오기
    private Long getUserIdFromJWT(String token) {
        String jwt = token;
        if (jwt == null || jwt.isEmpty()) {
            return null;
        }
        Jws<Claims> jws;
        try {
            jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt);
        } catch (JwtException e) {
            throw new RuntimeException(e);
        }
        return jws.getBody()
                .get("userId", Long.class);
    }

    // check JWT validation
    public boolean isValidToken(String token, Long userId) {
        try {
            // 로그인한 userId와 토큰의 userId가 일치 하는지 확인
            Long jwtUserId = getUserIdFromJWT(token);
            if(!isSameUserId(jwtUserId, userId)) {
                System.out.println("토큰의 userId와 로그인 한 userId 다름");
                throw new RuntimeException();
            }

            // 토큰의 유효 기간 확인
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            System.out.println("claimsJws: " + claimsJws);
            Date expirationDate = claimsJws.getBody().getExpiration();
            if(expirationDate.before(new Date())) {
                System.out.println("token is expired");
                return false;
            }
            return true;
        } catch (RuntimeException e) {
            System.out.println("토큰의 userId와 로그인한 userId가 일치하지 않습니다." + e.getMessage());
            return false;
        }
        catch (Exception e) {
            System.out.println("토큰 파싱 실패" + e.getMessage());
            return false;
        }

    }

    // JWT를 이용해 가져온 userId와 로그인 한 userId가 일치하는지 확인
    private boolean isSameUserId(Long userIdFromJWT, Long userId) {
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
    public String getRefreshToken(Long userId) {
        User user = userRepository.findByUserId(userId);
        String refreshToken = user.getRefreshToken();
        return refreshToken;
    }

    // refresh Token 만료 기간 확인
    public boolean isExpiredRefreshToken(String refreshToken) {
        Jws<Claims> claimsRefresh = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(refreshToken);
        System.out.println("claimsRefrsh: " + claimsRefresh);
        Date expirationDate = claimsRefresh.getBody().getExpiration();
        if(expirationDate.before(new Date())) {
            System.out.println("refresh token is expired");
            return false;
        }
        return true;
    }

    // jwt 만료 시간이 1분 미만이면, refresh token을 이용하여 재발급
    public String renewJWT(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken); // refreshToken의 user 가져오기
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(refreshToken);
        Date expirationDate = claimsJws.getBody().getExpiration();
        long timeDiff = expirationDate.getTime() - System.currentTimeMillis();
        String newJWT = null;
        if(timeDiff < 60 * 1000) { // 만료 1분 전
            newJWT = generateJWT(user.getUserId());
        }
        return newJWT;
    }
}
