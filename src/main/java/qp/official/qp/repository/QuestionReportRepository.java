package qp.official.qp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qp.official.qp.domain.mapping.QuestionReport;

@Repository
public interface QuestionReportRepository extends JpaRepository<QuestionReport, Long> {
}
