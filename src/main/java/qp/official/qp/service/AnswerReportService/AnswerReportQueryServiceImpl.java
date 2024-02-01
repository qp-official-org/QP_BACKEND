package qp.official.qp.service.AnswerReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qp.official.qp.domain.mapping.AnswerReport;
import qp.official.qp.repository.AnswerReportRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AnswerReportQueryServiceImpl implements AnswerReportQueryService{

    private final AnswerReportRepository answerReportRepository;
    @Override
    public AnswerReport getAnswerReport(Long reportId){
        return answerReportRepository.findById(reportId).get();
    }
}
