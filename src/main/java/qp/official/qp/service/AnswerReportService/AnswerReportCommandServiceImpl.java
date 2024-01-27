package qp.official.qp.service.AnswerReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qp.official.qp.converter.AnswerReportConverter;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.User;
import qp.official.qp.domain.mapping.AnswerReport;
import qp.official.qp.repository.AnswerReportRepository;
import qp.official.qp.repository.AnswerRepository;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.web.dto.AnswerReportRequestDTO;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AnswerReportCommandServiceImpl implements AnswerReportCommandService{

    private final AnswerReportRepository answerReportRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;

    @Override
    public AnswerReport createAnswerReport(AnswerReportRequestDTO.AnswerReportDTO request, Long answerId){
        AnswerReport answerReport = AnswerReportConverter.toAnswerReport(request);

        User user = userRepository.findById(request.getUserId()).get();
        Answer answer = answerRepository.findById(answerId).get();

        answerReport.setUser(user);
        answerReport.setAnswer(answer);
        return answerReportRepository.save(answerReport);
    }
}
