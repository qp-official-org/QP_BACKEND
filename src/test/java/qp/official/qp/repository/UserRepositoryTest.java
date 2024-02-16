package qp.official.qp.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qp.official.qp.domain.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    private final UserRepository userRepository;

    @Autowired
    UserRepositoryTest(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Test
    @DisplayName("existsByEmail 메소드 테스트")
    void existsByEmailTest(){
        // given
        String email = "test@naver.com";
        User user = User.builder()
            .nickname("testNickName")
            .email(email)
            .build();
        userRepository.save(user);
        // when
        boolean isExists = userRepository.existsByEmail(email);

        // then
        assertTrue(isExists);
    }

    @Test
    @DisplayName("findByEmail 메소드 테스트")
    void findByEmailTest(){
        // given
        String email = "test@naver.com";
        User user = User.builder()
            .nickname("testNickName")
            .email(email)
            .build();
        userRepository.save(user);

        // when
        Optional<User> findUser = userRepository.findByEmail(email);

        // then
        assertEquals(findUser.orElse(null), user);
    }
    @Test
    @DisplayName("findByEmail 메소드에 존재하지 않는 이메일 주소를 전달했을 때")
    void findByNonExistentEmailTest(){
        // given
        String email = "nonexistent@test.com";

        // when
        Optional<User> findUser = userRepository.findByEmail(email);

        // then
        assertNull(findUser.orElse(null));
    }

    @Test
    @DisplayName("existsByEmail 메소드에 존재하지 않는 이메일 주소를 전달했을 때")
    void existsByNonExistentEmailTest(){
        // given
        String email = "nonexistent@test.com";

        // when
        boolean isExists = userRepository.existsByEmail(email);

        // then
        assertFalse(isExists);
    }

}