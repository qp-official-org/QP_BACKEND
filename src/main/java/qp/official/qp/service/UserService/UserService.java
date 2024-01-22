package qp.official.qp.service.UserService;

import qp.official.qp.domain.User;
import qp.official.qp.web.dto.UserRequestDTO;
import qp.official.qp.web.dto.kakao.KakaoUserInfo;

import javax.transaction.Transactional;
import java.util.Map;

public interface UserService {

//    boolean isExistUser(KakaoUserInfo kakaoUserInfo);
    boolean signUp(KakaoUserInfo kakaoUserInfo);

    User getUser(KakaoUserInfo kakaoUserInfo);

    @Transactional
    Map<String, String> refresh(String refreshToken);

    User getUserInfo(Long userId);
    User updateUserInfo(Long userId, UserRequestDTO.UpdateUserInfoRequestDTO requestDTO);
}
