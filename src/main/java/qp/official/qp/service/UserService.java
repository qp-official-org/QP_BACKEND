package qp.official.qp.service;

import java.io.IOException;
import qp.official.qp.domain.User;
import qp.official.qp.web.dto.UserRequestDTO;

import java.util.Map;

public interface UserService {

    User getUserInfo(Long userId);
    User updateUserInfo(Long userId, UserRequestDTO.UpdateUserInfoRequestDTO requestDTO);
    User createTestUser();

    Map<String, String> refresh(String refreshToken);

    String getTokenByAuthorizeCode(String code) throws IOException;

    String getUserInfoByToken(String accessToken);


}
