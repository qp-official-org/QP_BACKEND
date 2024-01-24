package qp.official.qp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.Question;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Page<Answer> findAllByQuestion(Question question, PageRequest pageRequest);
}
