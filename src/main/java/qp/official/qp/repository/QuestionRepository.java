package qp.official.qp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qp.official.qp.domain.Question;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAllByOrderByCreatedAtDescQuestionIdDesc(Pageable pageable);

    Page<Question> findAllByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByCreatedAtDescQuestionIdDesc(String title,String content, Pageable pageable);
    /**
     * OlderQuestionsByQuestionId
     */
    Optional<Question> findTopByQuestionIdLessThanOrderByCreatedAtDescQuestionIdDesc(Long questionId);

    /**
     * LaterQuestionsByQuestionId
     * */
    Optional<Question> findTopByQuestionIdGreaterThanOrderByCreatedAtAscQuestionIdAsc(Long questionId);
}
