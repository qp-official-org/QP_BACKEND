package qp.official.qp.service;

import qp.official.qp.domain.User;
import qp.official.qp.web.dto.UserRequestDTO;


public interface UserService {

    User getUserInfo(Long userId);
    User updateUserInfo(Long userId, UserRequestDTO.UpdateUserInfoRequestDTO requestDTO);
    User createTestUser();

}
