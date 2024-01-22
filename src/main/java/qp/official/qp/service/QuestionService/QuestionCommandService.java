package qp.official.qp.service.QuestionService;

import qp.official.qp.domain.Question;
import qp.official.qp.web.dto.QuestionRequestDTO;

public interface QuestionCommandService {
    Question createQuestion(QuestionRequestDTO.CreateDTO request);
}
