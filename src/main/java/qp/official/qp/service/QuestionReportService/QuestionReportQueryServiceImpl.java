package qp.official.qp.service.QuestionReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qp.official.qp.domain.mapping.QuestionReport;
import qp.official.qp.repository.QuestionReportRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class QuestionReportQueryServiceImpl implements QuestionReportQueryService{

    private final QuestionReportRepository questionReportRepository;
    @Override
    public QuestionReport getQuestionReport(Long reportId){
        return questionReportRepository.findById(reportId).get();
    }
}
