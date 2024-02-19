package qp.official.qp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qp.official.qp.domain.Expert;

import java.util.List;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {
    Expert findByKakaoEmail(String kakaoEmail);
}
