package qp.official.qp.service.AnswerReportService;

import qp.official.qp.domain.mapping.AnswerReport;
import qp.official.qp.web.dto.AnswerReportRequestDTO;

public interface AnswerReportCommandService {
    AnswerReport createAnswerReport(AnswerReportRequestDTO.AnswerReportDTO request, Long answerId);
}
