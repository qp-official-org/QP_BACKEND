package qp.official.qp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import qp.official.qp.domain.User;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.web.dto.UserRequestDTO;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * userId를 통한 유저 정보 조회
     * @param userId
     * @return User
     */
    @Override
    public User getUserInfo(Long userId) {
        return userRepository.findById(userId).orElseThrow(NullPointerException::new);
    }

    /**
     * 유저 정보 수정
     * @param requestDTO
     * @return User
     */
    @Override
    @Transactional
    public User updateUserInfo(Long userId, UserRequestDTO.updateUserInfoRequestDTO requestDTO) {
        // 유저 정보 가져와야 함 (현재는 임시로 1번 유저)
        User user = userRepository.findById(userId).orElseThrow(NullPointerException::new);
        System.out.println(requestDTO.getNickname());
        System.out.println(requestDTO.getProfile_image());
        user.updateNickname(requestDTO.getNickname());
        user.updateProfileImage(requestDTO.getProfile_image());
        return user;
    }
}
