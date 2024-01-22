package qp.official.qp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qp.official.qp.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
