package qp.official.qp.service.QuestionService;

import qp.official.qp.domain.Question;
import qp.official.qp.domain.mapping.UserQuestionAlarm;
import qp.official.qp.web.dto.QuestionRequestDTO;

public interface QuestionCommandService {
    Question createQuestion(QuestionRequestDTO.CreateDTO request);

    Question updateQuestion(Long questionId, QuestionRequestDTO.UpdateDTO request);

    void deleteQuestion(Long questionId);

    UserQuestionAlarm saveQuestionAlarm(Long questionId, Long userId);
}
