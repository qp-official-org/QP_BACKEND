package qp.official.qp.service.QuestionReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qp.official.qp.converter.QuestionReportConverter;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.User;
import qp.official.qp.domain.mapping.QuestionReport;
import qp.official.qp.repository.QuestionReportRepository;
import qp.official.qp.repository.QuestionRepository;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.web.dto.QuestionReportRequestDTO;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuestionReportCommandServiceImpl implements QuestionReportCommandService{

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final QuestionReportRepository questionReportRepository;
    @Override
    public QuestionReport createQuestionReport(QuestionReportRequestDTO.QuestionReportDTO request, Long quesiontId){
        QuestionReport questionReport = QuestionReportConverter.toQuestionReport(request);

        User user = userRepository.findById(request.getUserId()).get();
        Question question = questionRepository.findById(quesiontId).get();

        questionReport.setUser(user);
        questionReport.setQuestion(question);
        return questionReportRepository.save(questionReport);
    }
}
