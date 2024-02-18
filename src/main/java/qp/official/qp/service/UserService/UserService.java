package qp.official.qp.service.UserService;

import org.json.simple.parser.ParseException;
import qp.official.qp.domain.User;
import qp.official.qp.web.dto.UserRequestDTO;
import java.io.IOException;
public interface UserService {

    User getUserInfo(Long userId);
    User updateUserInfo(Long userId, UserRequestDTO.UpdateUserInfoRequestDTO requestDTO);
    User createTestUser();
    User signUp(String code) throws IOException, ParseException;
    User autoSignIn(Long userId);
    User deleteUser(Long userId);
    User updateUserPoint(Long userId, UserRequestDTO.UpdateUserPointRequestDTO requestDTO);
}
