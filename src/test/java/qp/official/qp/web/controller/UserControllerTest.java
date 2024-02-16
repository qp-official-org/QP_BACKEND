package qp.official.qp.web.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.domain.User;
import qp.official.qp.domain.enums.Gender;
import qp.official.qp.domain.enums.UserStatus;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.service.TokenService.TokenServiceImpl;
import qp.official.qp.service.UserService.UserServiceImpl;
import qp.official.qp.web.dto.TokenDTO.TokenResponseDTO;
import qp.official.qp.web.dto.UserRequestDTO;
import qp.official.qp.web.dto.UserRequestDTO.AutoLoginRequestDTO;
import qp.official.qp.web.dto.UserRequestDTO.UpdateUserInfoRequestDTO;
import qp.official.qp.web.dto.UserResponseDTO;
@WebMvcTest(UserController.class)
class UserControllerTest {
    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private TokenServiceImpl tokenService;

    @MockBean
    private UserRepository userRepository;

    // MockMvc를 사용하여 API를 테스트
    private final MockMvc mockMvc;

    // ObjectMapper를 사용하여 객체를 JSON으로 변환 또는 JSON을 객체로 변환
    private final ObjectMapper objectMapper;

    @Autowired
    public UserControllerTest(
        MockMvc mockMvc,
        ObjectMapper objectMapper
    ) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void getKaKaoCode() throws Exception {
        // given
        Long userId = 1L;
        String kakaoAccessToken = "testSignUpToken";
        String userEmail = "testEmail";
        String userNickname = "testNickname";

        String accessToken = "testJwtToken";
        String refreshToken = "testRefreshToken";

        // Expect Response : User
        User userResponse = User.builder()
            .userId(userId)
            .email(userEmail)
            .nickname(userNickname)
            .build();

        // Expect Response : Token
        TokenResponseDTO tokenResponse = TokenResponseDTO.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();

        // userService.signUp
        when(userService.signUp(kakaoAccessToken)).thenReturn(userResponse);
        // tokenService.createToken
        when(tokenService.createToken(any(Long.class))).thenReturn(tokenResponse);

        // when
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/users/sign_up")
            .param("accessToken", kakaoAccessToken)
            .contentType(MediaType.APPLICATION_JSON));

        // then
        action.andExpect(status().isOk());

        ApiResponse<UserResponseDTO.UserSignUpResultDTO> response = objectMapper.readValue(
            action.andReturn().getResponse().getContentAsString(),
            new TypeReference<>() {
            }
        );

        // 검증
        assertEquals(userId, response.getResult().getUserId());
        assertEquals(accessToken, response.getResult().getAccessToken());
        assertEquals(refreshToken, response.getResult().getRefreshToken());
    }

    @Test
    void getUserInfo() throws Exception {
        // given
        Long userId = 1L;
        String accessToken = "testSignUpToken";
        String profileImage = "test.url";
        Long point = 0L;
        Gender gender = Gender.DEFAULT;
        String userEmail = "testEmail";
        String userNickname = "testNickname";

        User userResponse = User.builder()
            .userId(userId)
            .point(point)
            .gender(gender)
            .profileImage(profileImage)
            .email(userEmail)
            .nickname(userNickname)
            .build();

        // ExistUser Validator
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(User.builder().build()));
        // tokenService.isValidToken
        when(tokenService.isValidToken(any(Long.class))).thenReturn(null);
        // userService.getUserInfo
        when(userService.getUserInfo(any(Long.class))).thenReturn(userResponse);

        // when

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}", userId)
            .header("accessToken", accessToken)
            .contentType(MediaType.APPLICATION_JSON));

        // then

        ApiResponse<UserResponseDTO.GetUserInfoDTO> response = objectMapper.readValue(
            action.andReturn().getResponse().getContentAsString(),
            new TypeReference<>() {
            }
        );

        // 검증
        assertEquals(userNickname, response.getResult().getNickname());
        assertEquals(profileImage, response.getResult().getProfileImage());
        assertEquals(userEmail, response.getResult().getEmail());
        assertEquals(point, response.getResult().getPoint());
        assertEquals(gender, response.getResult().getGender());

    }

    @Test
    void autoSignIn() throws Exception {
        // given
        Long userId = 1L;
        String userEmail = "testEmail";
        String userNickname = "testNickname";

        String accessToken = "testJwtToken";
        String refreshToken = "testRefreshToken";

        User userResponse = User.builder()
            .userId(userId)
            .email(userEmail)
            .nickname(userNickname)
            .build();

        UserRequestDTO.AutoLoginRequestDTO request = AutoLoginRequestDTO.builder()
            .userId(userId)
            .build();

        TokenResponseDTO tokenResponse = TokenResponseDTO.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();

        // ExistUser Validator
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(User.builder().build()));
        // tokenService.autoSignIn
        when(tokenService.autoSignIn(any(Long.class))).thenReturn(tokenResponse);
        // userService.autoSignIn
        when(userService.autoSignIn(any(Long.class))).thenReturn(userResponse);

        // when
        String body = objectMapper.writeValueAsString(request);

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post("/users/auto_sign_in")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON));

        // then

        action.andExpect(status().isOk());

        ApiResponse<UserResponseDTO.AutoLoginDTO> response = objectMapper.readValue(
            action.andReturn().getResponse().getContentAsString(),
            new TypeReference<>() {
            }
        );

        // 검증

        assertEquals(userId, response.getResult().getUserId());
        assertEquals(accessToken, response.getResult().getAccessToken());
        assertEquals(refreshToken, response.getResult().getRefreshToken());

    }

    @Test
    void updateUserInfo() throws Exception {
        // given
        Long userId = 1L;
        String userEmail = "testEmail";

        String updateProfileImage = "updateProfileImage";
        String updateNickname = "updateNickname";
        LocalDateTime updatedAt = LocalDateTime.now();

        User userResponse = User.builder()
            .userId(userId)
            .email(userEmail)
            .nickname(updateNickname)
            .profileImage(updateProfileImage)
            .build();

        UserRequestDTO.UpdateUserInfoRequestDTO request = UpdateUserInfoRequestDTO.builder()
            .nickname(updateNickname)
            .profileImage(updateProfileImage)
            .build();

        ReflectionTestUtils.setField(userResponse, "updatedAt", updatedAt);

        // ExistUser Validator
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(User.builder().build()));
        // tokenService.isValidToken
        when(tokenService.isValidToken(any(Long.class))).thenReturn(null);
        // userService.updateUserInfo
        when(userService.updateUserInfo(any(Long.class), any(UpdateUserInfoRequestDTO.class))).thenReturn(userResponse);

        // when
        String body = objectMapper.writeValueAsString(request);

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.patch("/users/{userId}", userId)
            .content(body)
            .contentType(MediaType.APPLICATION_JSON));

        // then
        action.andExpect(status().isOk());

        ApiResponse<UserResponseDTO.UpdateUserInfoDTO> response = objectMapper.readValue(
            action.andReturn().getResponse().getContentAsString(),
            new TypeReference<>() {
            }
        );

        // 검증
        assertEquals(userId, response.getResult().getUserId());
        assertEquals(updateNickname, response.getResult().getNickname());
        assertEquals(updateProfileImage, response.getResult().getProfileImage());
        assertEquals(updatedAt, response.getResult().getUpdatedAt());
    }

    @Test
    void delete() throws Exception {
        // given
        Long userId = 1L;
        String userEmail = "testEmail";
        String userNickname = "testNickname";
        UserStatus userStatus = UserStatus.DELETED;
        LocalDateTime updatedAt = LocalDateTime.now();

        User userResponse = User.builder()
            .userId(userId)
            .email(userEmail)
            .nickname(userNickname)
            .status(userStatus)
            .build();

        ReflectionTestUtils.setField(userResponse, "updatedAt", updatedAt);

        // ExistUser Validator
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(User.builder().build()));
        // tokenService.isValidToken
        when(tokenService.isValidToken(any(Long.class))).thenReturn(null);
        // userService.deleteUser
        when(userService.deleteUser(any(Long.class))).thenReturn(userResponse);

        // when
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.patch("/users/delete/{userId}", userId)
            .contentType(MediaType.APPLICATION_JSON));

        // then
        action.andExpect(status().isOk());

        ApiResponse<UserResponseDTO.deleteUserDTO> response = objectMapper.readValue(
            action.andReturn().getResponse().getContentAsString(),
            new TypeReference<>() {
            }
        );

        // 검증
        assertEquals(userId, response.getResult().getUserId());
        assertEquals(updatedAt, response.getResult().getUpdatedAt());
        assertEquals(userStatus, response.getResult().getStatus());

    }

}

