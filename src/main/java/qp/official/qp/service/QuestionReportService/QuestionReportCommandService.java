package qp.official.qp.service.QuestionReportService;

import qp.official.qp.domain.mapping.QuestionReport;
import qp.official.qp.web.dto.QuestionReportRequestDTO;

public interface QuestionReportCommandService {
    QuestionReport createQuestionReport(QuestionReportRequestDTO.QuestionReportDTO request, Long quesiontId);
}
