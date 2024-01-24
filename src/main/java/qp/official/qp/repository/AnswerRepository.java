package qp.official.qp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.enums.Role;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Integer countByQuestionAndUserRole(Question question, Role role);
}
