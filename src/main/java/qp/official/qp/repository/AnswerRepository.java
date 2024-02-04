package qp.official.qp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.enums.Category;
import qp.official.qp.domain.enums.Role;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Page<Answer> findByQuestionAndCategoryOrderByCreatedAtDescAnswerIdDesc(Question question, Category category, Pageable pageable);

    Page<Answer> findByAnswerGroupOrderByCreatedAtDescAnswerIdDesc(Long answerGroup, Pageable pageable);
    Integer countByQuestionAndUserRole(Question question, Role role);
}
