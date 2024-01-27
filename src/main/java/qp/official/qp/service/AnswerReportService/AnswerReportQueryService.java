package qp.official.qp.service.AnswerReportService;

import qp.official.qp.domain.mapping.AnswerReport;

public interface AnswerReportQueryService {

    AnswerReport findAnswerReportByReportId(Long reportId, Long answerId);

}
