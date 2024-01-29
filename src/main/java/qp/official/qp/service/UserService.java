package qp.official.qp.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import qp.official.qp.domain.User;
import qp.official.qp.web.dto.UserAuthDTO;
import qp.official.qp.web.dto.UserRequestDTO;

import java.util.Map;
import qp.official.qp.web.dto.UserResponseDTO;

public interface UserService {

    User getUserInfo(Long userId);
    User updateUserInfo(Long userId, UserRequestDTO.UpdateUserInfoRequestDTO requestDTO);
    User createTestUser();

    Map<String, String> refresh(String refreshToken);

    String toKakaoLogin();

    UserResponseDTO.UserSignUpResultDTO signUp(String code) throws IOException;

    void reset();
}
