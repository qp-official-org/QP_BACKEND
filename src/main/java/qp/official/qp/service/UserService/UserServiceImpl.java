package qp.official.qp.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import qp.official.qp.domain.User;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.service.UserService.UserService;
import qp.official.qp.util.JwtUtil;
import qp.official.qp.web.dto.UserRequestDTO;
import qp.official.qp.web.dto.kakao.KakaoUserInfo;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public boolean signUp(KakaoUserInfo kakaoUserInfo) {
        try {
            User user = User
                    .builder()
                    .refreshToken(JwtUtil.generateRefreshToken())
                    .refreshTokenExpiresAt(LocalDateTime.now().plusMonths(JwtUtil.REFRESH_TOKEN_EXPIRES_MONTH))
                    .build();

            userRepository.save(user);
            return true;
        } catch (Exception e) {
            System.out.println("회원가입 실패");
            return false;
        }
    }

    @Override
    public User getUser(KakaoUserInfo kakaoUserInfo) {
        // 수정 필요
        User user = userRepository.findByRefreshToken(kakaoUserInfo.getRefreshToken());
        if (user == null) {
            throw new NullPointerException("존재하지 않는 유저입니다.");
        }
        return user;
    }

    @Override
    @Transactional
    public Map<String, String> refresh(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken);
        if (user.getRefreshTokenExpiresAt().isBefore(LocalDateTime.now())) {
//            throw new TokenExpiredException("만료된 토큰입니다.");
            System.out.println("만료된 토큰입니다.");
        }

        Map<String, String> tokenMap = new HashMap<>();
        Date nextMonth = Date.from(LocalDateTime.now().plusMonths(1).atZone(ZoneId.of("Asia/Seoul")).toInstant());
        // 1개월 미만 남은 경우 refresh_token 을 추가한다.
        if (user.getRefreshTokenExpiresAt().isBefore(LocalDateTime.ofInstant(nextMonth.toInstant(), ZoneId.of("Asia/Seoul")))) {
            user.setRefreshToken(JwtUtil.generateRefreshToken());
            user.setRefreshTokenExpiresAt(LocalDateTime.now().plusMonths(JwtUtil.REFRESH_TOKEN_EXPIRES_MONTH));
            tokenMap.put("refresh_token", user.getRefreshToken());
        }

        tokenMap.put("access_token", JwtUtil.generateJwt(user));

        return tokenMap;
    }

    /**
     * userId를 통한 유저 정보 조회
     * @param userId
     * @return User
     */
    @Override
    public User getUserInfo(Long userId) {
        return userRepository.findById(userId).orElseThrow(NullPointerException::new);
    }

    /**
     * 유저 정보 수정
     * @param requestDTO
     * @return User
     */
    @Override
    @Transactional
    public User updateUserInfo(Long userId, UserRequestDTO.UpdateUserInfoRequestDTO requestDTO) {
        User user = userRepository.findById(userId).orElseThrow(NullPointerException::new);
        user.updateNickname(requestDTO.getNickname());
        user.updateProfileImage(requestDTO.getProfile_image());
        return user;
    }
}
