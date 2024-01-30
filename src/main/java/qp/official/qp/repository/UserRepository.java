package qp.official.qp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qp.official.qp.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Long userId);

    User findByEmail(String email);
    Optional<User> findByRefreshToken(String refreshToken);

    boolean existsByEmail(String email);


}
