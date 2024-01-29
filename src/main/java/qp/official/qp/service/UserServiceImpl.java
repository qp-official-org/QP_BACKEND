package qp.official.qp.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import qp.official.qp.domain.User;
import qp.official.qp.domain.enums.Gender;
import qp.official.qp.domain.enums.Role;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.web.dto.UserRequestDTO;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JWTService jwtService;

    /**
     * userId를 통한 유저 정보 조회
     *
     * @param userId
     * @return User
     */
    @Override
    public User getUserInfo(Long userId) {
        return userRepository.findById(userId).orElseThrow(NullPointerException::new);
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
        User user = userRepository.findById(userId).orElseThrow(NullPointerException::new);
        user.updateNickname(requestDTO.getNickname());
        user.updateProfileImage(requestDTO.getProfile_image());
        return user;
    }

    @Override
    public User createTestUser() {

        User newUser = User.builder()
                .name("Test_Name")
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
    public String getTokenByAuthorizeCode(String code) throws IOException {
        String host = "https://kauth.kakao.com/oauth/token"; // 리다이렉트 보낼 URL
        URL url = new URL(host);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String token = "";
        try {

            urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            urlConnection.setRequestMethod("POST"); // POST 메소드로 보냄
            urlConnection.setDoOutput(true); // 기록 보여주기

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("grant_type=authorization_code");
            stringBuilder.append("&client_id=5f7c349cebbbf1716e105b687b6428b7");
            stringBuilder.append("&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fusers%2Fsign_up");
            stringBuilder.append("&code=" + code);

            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();

            int responseCode = urlConnection.getResponseCode();
            log.info("responseCode : {}", responseCode);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            log.info("result : {}",  result);

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);

            String accessToken = jsonObject.get("access_token").toString();
            String refreshToken = jsonObject.get("refresh_token").toString();
            log.info("accessToken : {}", accessToken);
            log.info("refreshToken : {}", refreshToken);

            token = accessToken;
            bufferedReader.close();
            bufferedWriter.close();

        }catch (IOException e){
            e.printStackTrace();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return token;
    }


    @Override
    public HashMap<String, Object> getUserInfoByToken(String accessToken) throws IOException {
        HashMap<String, Object> userInfo = new HashMap<>();
        String redirectUrl = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(redirectUrl);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + accessToken);
            urlConnection.setRequestMethod("GET");

            int responseCode = urlConnection.getResponseCode();
            log.info("responseCode : {}", responseCode);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String res = "";
            while((line=bufferedReader.readLine())!=null)
            {
                res+=line;
            }

            System.out.println("res = " + res);

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(res);

            JSONObject kakao_account = (JSONObject) jsonObject.get("kakao_account");
            JSONObject properties = (JSONObject) jsonObject.get("properties");
            JSONObject profile = (JSONObject) kakao_account.get("profile");


            String id = jsonObject.get("id").toString();
            String nickname = properties.get("nickname").toString();
            String email = kakao_account.get("email").toString();
            String profileImageUrl = profile.get("profile_image_url").toString();



            userInfo.put("id", id);
            userInfo.put("nickname", nickname);
            userInfo.put("accessToken", accessToken);
            userInfo.put("email", email);
            userInfo.put("profileImageUrl", profileImageUrl);

            bufferedReader.close();

        }catch (IOException | ParseException e){
            e.printStackTrace();
        }
        return userInfo;
    }
}
