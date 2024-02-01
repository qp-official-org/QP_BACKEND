package qp.official.qp.service.TokenService;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
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
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService{
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
        String token = request.getHeader("access_token"); // jwt
        return token;
    }

    // JWT Token 을 이용해 userId 가져오기
    private Long getUserIdFromJWT(String token) throws JwtException{
        String jwt = token;
        if (jwt == null || jwt.isEmpty()) {
            throw new TokenHandler(ErrorStatus.TOKEN_NOT_EXIST);
        }
        Jws<Claims> jws;
        jws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt);

        return jws.getBody()
                .get("userId", Long.class);
    }

    // check JWT validation
    public boolean isValidToken(String token, Long userId) {
        try {
            // 로그인한 userId와 토큰의 userId가 일치 하는지 확인
            Long jwtUserId = getUserIdFromJWT(token);
            if (!isSameUserId(jwtUserId, userId)) {
                throw new TokenHandler(ErrorStatus.TOKEN_NOT_MATCH);
            }
            return true;
        }
        // 토큰의 유효 기간 확인
        catch(ExpiredJwtException e){
            return false;
        }
        catch(JwtException e) {
            System.out.println("토큰 파싱 실패" + e.getMessage());
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
    }

    // isValidToken을 이용해 유효성 검사 후 만료되었으면 TOKEN_EXPIRED에러 반환
    public void checkTokenValid(String token, Long userId) {
        if(!isValidToken(token, userId)){
            throw new TokenHandler(ErrorStatus.TOKEN_EXPIRED);
        }
    }

    // JWT를 이용해 가져온 userId와 로그인 한 userId가 일치하는지 확인
    private boolean isSameUserId(Long userIdFromJWT, Long userId) {
        // jwt의 userId가 null인 예외 처리
        if (userIdFromJWT == null) {
            throw new GeneralException(ErrorStatus._BAD_REQUEST);
        }
        // 같으면 true, 다르면 false 리턴
        return userIdFromJWT.equals(userId);
    }

    // Generate Refresh Token
    @Transactional
    public String generateRefreshToken(Long userId) {
        Date now = new Date();
        String refreshToken = Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRED_TIME * 1000))
                .signWith(key)
                .compact();
        User user = userRepository.findById(userId).get();
        user.setRefreshToken(refreshToken);
        return refreshToken;
    }

    // get Refresh Token
    public String getRefreshToken(Long userId) {
        User user = userRepository.findById(userId).get();
        String refreshToken = user.getRefreshToken();
        return refreshToken;
    }

    // refresh Token 만료 기간 확인
    public boolean isExpiredRefreshToken(String refreshToken) {
        Jws<Claims> claimsRefresh = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(refreshToken);
        Date expirationDate = claimsRefresh.getBody().getExpiration();
        if(expirationDate.before(new Date())) {
            throw new TokenHandler(ErrorStatus.TOKEN_EXPIRED);
        }
        return true;
    }

    // jwt 만료 시간이 1분 미만이면, refresh token을 이용하여 재발급
    public String renewJWT(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken); // refreshToken의 user 가져오기
        String jwtoken = getJWT();

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtoken);
        Date expirationDate = claimsJws.getBody().getExpiration();
        long timeDiff = expirationDate.getTime() * 1000 - System.currentTimeMillis() * 1000;

        String token = expirationDate.toString();
        if(timeDiff < 60 * 1000) { // 만료 1분 전이면 jwt 재발급
            token = generateJWT(user.getUserId());
        }
        return token;
    }

    @Override
    public TokenResponseDTO createToken(Long userId) {
        return null;
    }

    @Override
    public TokenResponseDTO isValidToken(Long userId) {
        return null;
    }
}