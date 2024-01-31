package qp.official.qp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qp.official.qp.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Long userId);

    User findByEmail(String email);

    boolean existsByEmail(String email);

    User findByRefreshToken(String refreshToken);

}
