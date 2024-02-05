package qp.official.qp.service.UserService;


import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import qp.official.qp.apiPayload.code.BaseErrorCode;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.apiPayload.exception.handler.UserHandler;
import qp.official.qp.converter.UserConverter;
import qp.official.qp.domain.User;
import qp.official.qp.domain.enums.Gender;
import qp.official.qp.domain.enums.Role;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.web.dto.UserAuthDTO.KaKaoUserInfoDTO;
import qp.official.qp.web.dto.UserRequestDTO;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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

    @Override
    @Transactional
    public User signUp(String accessToken){
        KaKaoUserInfoDTO userInfo;
        try {
            userInfo = getUserInfoByToken(accessToken);
        }catch (IOException e){
            throw new UserHandler(ErrorStatus.TOKEN_NOT_INCORRECT);
        }

        String email = userInfo.getKakao_account().getEmail();

        User newUser = UserConverter.toUserDTO(email, userInfo.getProperties().getNickname());

        return userRepository.save(newUser);
    }

    @Override
    public User autoSignIn(Long userId) {
        return userRepository.findById(userId).get();
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
            throw new RuntimeException(e);
        }

        return token;
    }

}
