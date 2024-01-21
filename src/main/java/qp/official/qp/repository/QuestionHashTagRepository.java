package qp.official.qp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qp.official.qp.domain.mapping.QuestionHashTag;

@Repository
public interface QuestionHashTagRepository extends JpaRepository<QuestionHashTag, Long> {
}
