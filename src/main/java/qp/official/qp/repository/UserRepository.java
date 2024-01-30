package qp.official.qp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qp.official.qp.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByRefreshToken(String refreshToken);

}
