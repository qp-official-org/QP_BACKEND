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
import qp.official.qp.converter.TokenConverter;
import qp.official.qp.domain.User;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.web.dto.TokenDTO.AccessTokenDTO;
import qp.official.qp.web.dto.TokenDTO.RefreshTokenDTO;
import qp.official.qp.web.dto.TokenDTO.TokenResponseDTO;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {
    private String secretKey;
    private SecretKey key;

    @Autowired
    public TokenServiceImpl(
            @Value("${JWT_SECRET}") String secretKey,
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
        this.secretKey = secretKey;
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public static final int ACCESS_TOKEN_EXPIRED_TIME = 60 * 60 * 2 * 1000; // 2 hours
    public static final int REFRESH_TOKEN_EXPIRED_TIME = 60 * 60 * 24 * 14 * 1000; // 14 days
    private final UserRepository userRepository;


    // Generate Refresh Token
    @Override
    public TokenResponseDTO autoSignIn(Long userId) {
        String accessToken = getToken("accessToken");
        String refreshToken = getToken("refreshToken");

        // Access Token 유효성 검사 (서명키)
        AccessTokenDTO accessTokenDTO = TokenConverter.parseAccessToken(accessToken, key);
        // Refresh Token 유효성 검사 (서명키)
        RefreshTokenDTO refreshTokenDTO = TokenConverter.parseRefreshToken(refreshToken, key);

        // 만약 Refresh Token이 만료되었다면 재로그인을 요청하도록 한다.
        if (refreshTokenDTO.isExpired()) {
            throw new TokenHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        // 로그인한 userId와 토큰의 userId가 일치 하는지 확인
        if (!accessTokenDTO.isMatchUserId(userId) || !refreshTokenDTO.isMatchUserId(userId)) {
            // 로그인한 userId와 토큰의 userId가 일치하지 않는다면, 재로그인을 요청하도록 한다.
            throw new TokenHandler(ErrorStatus.TOKEN_NOT_MATCH);
        }

        // AccessToken 토큰의 만료 시간이 20분 미만이면
        if (accessTokenDTO.isAlmostExpiredByMinutes(20)) {
            // AccessToken 재발급
            accessTokenDTO = createAccessToken(userId);
        }

        return TokenConverter.createTokenResponseDTO(accessTokenDTO, refreshTokenDTO);
    }

    @Override
    public TokenResponseDTO isValidToken(Long userId) {

        // 토큰 가져오기
        String accessToken = getToken("accessToken");

        // 토큰 유효성 검사 (서명키)
        AccessTokenDTO accessTokenDTO = TokenConverter.parseAccessToken(accessToken, key);

        // 토큰 유효성 검사 (userId)

        if (!accessTokenDTO.isMatchUserId(userId)) {
            throw new TokenHandler(ErrorStatus.TOKEN_NOT_MATCH);
        }

        // 토큰 유효성 검사 (만료)
        if (accessTokenDTO.isExpired()) {
            throw new TokenHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        // Refresh Token 가져오기
        String storedRefreshToken = userRepository.findById(userId).get().getRefreshToken();
        RefreshTokenDTO refreshTokenDTO = TokenConverter.parseRefreshToken(storedRefreshToken, key);

        return TokenConverter.createTokenResponseDTO(accessTokenDTO, refreshTokenDTO);
    }

    @Override
    public TokenResponseDTO createToken(Long userId) {
        // Access Token, Refresh Token 생성
        AccessTokenDTO accessToken = createAccessToken(userId);
        RefreshTokenDTO refreshToken = createRefreshToken(userId);

        // DB에 저장
        User findUser = userRepository.findById(userId).get();
        findUser.setRefreshToken(refreshToken);
        userRepository.save(findUser);

        // Response
        return TokenConverter.createTokenResponseDTO(accessToken, refreshToken);
    }

    private RefreshTokenDTO createRefreshToken(Long userId) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRED_TIME);

        String token = generateToken(userId, now, expiredDate);

        return TokenConverter.createRefreshTokenDTO(userId, token, now, expiredDate);
    }

    private AccessTokenDTO createAccessToken(Long userId) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRED_TIME);

        String token = generateToken(userId, now, expiredDate);

        return TokenConverter.createAccessTokenDTO(userId, token, now, expiredDate);
    }

    private String generateToken(Long userId, Date now, Date expiredDate) {
        return Jwts.builder()
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(key)
                .compact();// payload
    }

    // Get Token
    private String getToken(String tokenName) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String token = request.getHeader(tokenName);

        if (token == null) {
            throw new TokenHandler(ErrorStatus.TOKEN_NOT_EXIST);
        }

        return token;
    }
}