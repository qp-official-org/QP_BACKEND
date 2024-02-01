package qp.official.qp.service;

import org.json.simple.parser.ParseException;
import qp.official.qp.domain.User;
import qp.official.qp.web.dto.UserRequestDTO;
import qp.official.qp.web.dto.UserResponseDTO;
import java.io.IOException;
import java.util.Map;
public interface UserService {

    User getUserInfo(Long userId);
    User updateUserInfo(Long userId, UserRequestDTO.UpdateUserInfoRequestDTO requestDTO);
    User createTestUser();

    Map<String, String> refresh(String refreshToken);
    UserResponseDTO.UserSignUpResultDTO signUp(String code) throws IOException, ParseException;

}
