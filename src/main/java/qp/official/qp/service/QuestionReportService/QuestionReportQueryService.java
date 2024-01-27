package qp.official.qp.service.QuestionReportService;

import qp.official.qp.domain.mapping.QuestionReport;
import qp.official.qp.web.dto.QuestionReportRequestDTO;

public interface QuestionReportQueryService {

    QuestionReport getQuestionReport(Long reportId);
}
