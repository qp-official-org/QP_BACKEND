package qp.official.qp.service.TokenService;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.time.ZoneId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.apiPayload.exception.GeneralException;
import qp.official.qp.apiPayload.exception.handler.TokenHandler;
import qp.official.qp.domain.User;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.web.dto.TokenResponseDTO;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {
    private String secretKey;
    private SecretKey key;

    @Autowired
    public TokenServiceImpl(@Value("${JWT_SECRET}") String secretKey, UserRepository userRepository) {
        this.secretKey = secretKey;
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.userRepository = userRepository;
    }

    public static final int JWT_EXPIRED_TIME = 60 * 60 * 2; // 2 hours
    public static final int REFRESH_TOKEN_EXPIRED_TIME = 60 * 60 * 24 * 14; // 14 days
    private final UserRepository userRepository;


    // Generate JWT Token
    public String generateJWT(Long userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRED_TIME * 1000))
                .signWith(key)
                .compact();// payload
    }

    // Get JWT Token
    public String getJWT() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String token = request.getHeader("accessToken"); // jwt

        if (token == null || token.isEmpty()) {
            throw new TokenHandler(ErrorStatus.TOKEN_NOT_EXIST);
        }
        return token;
    }

    // JWT Token 을 이용해 userId 가져오기
    private Long getUserIdFromJWT(String token) throws JwtException {
        String jwt = token;
        if (jwt == null || jwt.isEmpty()) {
            throw new TokenHandler(ErrorStatus.TOKEN_NOT_EXIST);
        }
        Jws<Claims> jws;

        try {
            jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt);
        }
        catch (Exception e){
            throw new TokenHandler(ErrorStatus.TOKEN_NOT_MATCH);
        }

        return jws.getBody()
                .get("userId", Long.class);
    }

    // check JWT validation
    public boolean isValidToken(String token, Long userId) {
        try {
            // 로그인한 userId와 토큰의 userId가 일치 하는지 확인
            Long jwtUserId = getUserIdFromJWT(token);
            if (jwtUserId.longValue() != userId.longValue()) {
                throw new TokenHandler(ErrorStatus.TOKEN_NOT_MATCH);
            }
            return true;
        }
        // 토큰의 유효 기간 확인
        catch (ExpiredJwtException e) {
            return false;
        } catch (JwtException e) {
            System.out.println("토큰 파싱 실패" + e.getMessage());
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
    }

    // Generate Refresh Token
    public String generateRefreshToken(Long userId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + REFRESH_TOKEN_EXPIRED_TIME * 1000);
        String refreshToken = Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .claim("userId", userId)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
        User user = userRepository.findById(userId).get();
        user.setRefreshToken(refreshToken);
        user.setRefreshTokenExpiresAt(expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return refreshToken;
    }

    // get Refresh Token
    public String getRefreshToken(Long userId) {
        User user = userRepository.findById(userId).get();
        return user.getRefreshToken();
    }

    // refresh Token 만료 기간 확인
    public boolean isExpiredRefreshToken(String refreshToken) {
        Jws<Claims> claimsRefresh = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(refreshToken);
        Date expirationDate = claimsRefresh.getBody().getExpiration();
        if (expirationDate.before(new Date())) {
            throw new TokenHandler(ErrorStatus.TOKEN_EXPIRED);
        }
        return true;
    }

    @Override
    public TokenResponseDTO createToken(Long userId) {
        User findUser = userRepository.findById(userId).get();

        if (findUser.getRefreshToken() != null){
            return TokenResponseDTO.builder()
                .accessToken(getJWT())
                .refreshToken(findUser.getRefreshToken())
                .build();
        }

        String refreshToken = generateRefreshToken(userId);
        findUser.setRefreshToken(refreshToken);
        userRepository.save(findUser);

        return TokenResponseDTO.builder()
                .accessToken(generateJWT(userId))
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenResponseDTO isValidToken(Long userId) {

        String accessToken = getJWT();
        String refreshToken = getRefreshToken(userId);

        if (!isValidToken(accessToken, userId)) {
            throw new TokenHandler(ErrorStatus.TOKEN_EXPIRED);
        }
        isExpiredRefreshToken(refreshToken);

        return TokenResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenResponseDTO autoSignIn(Long userId) {
        String accessToken = getJWT();
        String refreshToken = getRefreshToken(userId);

        // Refresh Token 유효성 검사
        // 만약 Refresh Token이 만료되었다면, 재로그인을 요청하도록 한다.
        isExpiredRefreshToken(refreshToken);

        Long getUserIdFromAccessToken = getUserIdFromJWT(accessToken);

        // 로그인한 userId와 토큰의 userId가 일치 하는지 확인
        if (getUserIdFromAccessToken.longValue() != userId.longValue()) {
            // 로그인한 userId와 토큰의 userId가 일치하지 않는다면, 재로그인을 요청하도록 한다.
            throw new TokenHandler(ErrorStatus.TOKEN_NOT_MATCH);
        }

        // Access Token 유효성 검사
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken);

        // AccessToken 만료 시간 가져오기
        Date expirationDate = claims.getBody().getExpiration();
        // AccessToken 토큰의 만료 시간이 20분 미만이면, refresh token을 이용하여 재발급
        long timeDiff = expirationDate.getTime() - new Date().getTime();
//        if (timeDiff > 60 * 20 * 1000) { // 만료 20분 전이면 AccessToken 재발급
        if(timeDiff > 0){
            accessToken = generateJWT(userId);
            System.out.print("accessToken 재발급");
        }

        return TokenResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}