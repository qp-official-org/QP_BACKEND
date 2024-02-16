package qp.official.qp.service.UserService;


import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.apiPayload.exception.handler.UserHandler;
import qp.official.qp.converter.UserConverter;
import qp.official.qp.domain.User;
import qp.official.qp.domain.enums.Gender;
import qp.official.qp.domain.enums.Role;
import qp.official.qp.domain.enums.UserStatus;
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
import java.util.Optional;

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

        String updateNickname = requestDTO.getNickname();
        String updateProfileImage = requestDTO.getProfileImage();

        if (updateNickname != null && !updateNickname.isEmpty()) {
            user.updateNickname(requestDTO.getNickname());
        }

        if (updateProfileImage != null && !updateProfileImage.isEmpty()) {
            user.updateProfileImage(requestDTO.getProfileImage());
        }

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
    public User signUp(String accessToken) {
        KaKaoUserInfoDTO userInfo;

        userInfo = getUserInfoByToken(accessToken);

        String email = userInfo.getKakao_account().getEmail();

        Optional<User> findUserOpt = userRepository.findByEmail(email);

        User getUser = findUserOpt.orElseGet(
                () -> UserConverter.toUser(email, userInfo.getProperties().getNickname())
        );

        System.out.println("findUserOpt = " + findUserOpt);

        getUser.setIsNew(findUserOpt.isEmpty());

        return userRepository.save(getUser);
    }

    @Override
    public User autoSignIn(Long userId) {
        return userRepository.findById(userId).get();
    }

    private KaKaoUserInfoDTO getUserInfoByToken(String accessToken) {
        String redirectUrl = "https://kapi.kakao.com/v2/user/me";
        StringBuilder res;

        try {
            URL url = new URL(redirectUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + accessToken);
            urlConnection.setRequestMethod("GET");


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            res = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                res.append(line);
            }

        } catch (IOException e) {
            throw new UserHandler(ErrorStatus.TOKEN_NOT_INCORRECT);
        }

        return gson.fromJson(res.toString(), KaKaoUserInfoDTO.class);
    }

    @Override
    public User deleteUser(Long userId) {
        User user = userRepository.findById(userId).get();
        user.updateStatus(UserStatus.DELETED);
        userRepository.save(user);
        return user;
    }

}
