package qp.official.qp.service;



import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qp.official.qp.converter.UserConverter;
import qp.official.qp.domain.User;
import qp.official.qp.domain.enums.Gender;
import qp.official.qp.domain.enums.Role;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.service.TokenService.TokenService;
import qp.official.qp.web.dto.UserAuthDTO.KaKaoUserInfoDTO;
import qp.official.qp.web.dto.UserRequestDTO;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import qp.official.qp.web.dto.UserResponseDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final TokenService tokenService;
    private final Gson gson;

    /**
     * userId를 통한 유저 정보 조회
     *
     * @param userId
     * @return User
     */
    @Override
    public User getUserInfo(Long userId) {
        return userRepository.findById(userId).get();
    }

    /**
     * 유저 정보 수정
     *
     * @param requestDTO
     * @return User
     */
    @Override
    @Transactional
    public User updateUserInfo(Long userId, UserRequestDTO.UpdateUserInfoRequestDTO requestDTO) {
        User user = userRepository.findById(userId).get();
        user.updateNickname(requestDTO.getNickname());
        user.updateProfileImage(requestDTO.getProfile_image());
        return user;
    }

    @Override
    public User createTestUser() {

        User newUser = User.builder()
                .email("Test_Email")
                .point(0L)
                .lastLogin(LocalDateTime.now())
                .profileImage("https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fe29b9403-b3ec-44bc-8253-1cf1ad90d216%2Fb6d5f75a-3365-4d41-b12c-01cdf95b2985%2FUntitled.png?table=block&id=ea6c2693-588c-4707-b478-fac2c4cab6b9&spaceId=e29b9403-b3ec-44bc-8253-1cf1ad90d216&width=2000&userId=2d84c7f9-2777-4bd6-aa0b-c963f16a6468&cache=v2")
                .role(Role.USER)
                .nickname("testNickName")
                .gender(Gender.DEFAULT)
                .build();

        return userRepository.save(newUser);
    }


    // refresh 토큰 재발급
    // refresh 토큰으로 유저 조회 -> 이메일로 해야 하나?
    @Override
    @Transactional
    public Map<String, String> refresh(String refreshToken) {
//        User user = userRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RuntimeException("해당하는 회원이 없습니다."));
//        if (user.getRefreshTokenExpiresAt().isBefore(LocalDateTime.now())) {
//            throw new RuntimeException("토큰이 만료되었습니다.");
//        }
        Map<String, String> tokenMap = new HashMap<>();
//        Date nextWeek = Date.from(LocalDateTime.now().plusWeeks(1).atZone(ZoneId.of("Asia/Seoul")).toInstant());
//        if (user.getRefreshTokenExpiresAt().isBefore(LocalDateTime.ofInstant(nextWeek.toInstant(), ZoneId.of("Asia/Seoul")))) {
//            user.setRefreshToken(JWTService.generateRefreshToken());
//            user.setRefreshTokenExpiresAt(LocalDateTime.now().plusWeeks(JWTService.REFRESH_TOKEN_EXPIRED_TIME));
//        }
//        System.out.println("JwtUtil.generateJwt(user):: " + jwtService.createJWT(user.getUserId()));
//        tokenMap.put("access_token", jwtService.createJWT(user.getUserId()));
        return tokenMap;
    }

    @Override
    @Transactional
    public UserResponseDTO.UserSignUpResultDTO signUp(String accessToken) throws IOException {
        KaKaoUserInfoDTO userInfo = getUserInfoByToken(accessToken);
        String email = userInfo.getKakao_account().getEmail();

        if (userRepository.existsByEmail(userInfo.getKakao_account().getEmail())){
            User user = userRepository.findByEmail(email);
            return UserConverter.toUserSignUpResultDTO(tokenService.getJWT(), user.getRefreshToken());
        }

        User newUser = userRepository.save(UserConverter.toUserDTO(email, userInfo.getProperties().getNickname()));

        String jwtToken = tokenService.generateJWT(newUser.getUserId());
        String refreshToken = tokenService.generateRefreshToken(newUser.getUserId());
        newUser.setRefreshToken(refreshToken);

        return UserConverter.toUserSignUpResultDTO(jwtToken, refreshToken);
    }


    private KaKaoUserInfoDTO getUserInfoByToken(String accessToken) throws IOException {

        String redirectUrl = "https://kapi.kakao.com/v2/user/me";

        URL url = new URL(redirectUrl);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Authorization", "Bearer " + accessToken);
        urlConnection.setRequestMethod("GET");


        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String line = "";
        StringBuilder res = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            res.append(line);
        }

        return gson.fromJson(res.toString(), KaKaoUserInfoDTO.class);
    }
}
