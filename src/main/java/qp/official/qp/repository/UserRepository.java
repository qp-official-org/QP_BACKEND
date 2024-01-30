package qp.official.qp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qp.official.qp.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByRefreshToken(String refreshToken);

}
