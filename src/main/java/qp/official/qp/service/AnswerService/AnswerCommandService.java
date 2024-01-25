package qp.official.qp.service.AnswerService;

import qp.official.qp.domain.Answer;
import qp.official.qp.web.dto.AnswerLikeRequestDTO;
import qp.official.qp.web.dto.AnswerRequestDTO;

public interface AnswerCommandService {
    public Answer createAnswer(AnswerRequestDTO.AnswerCreateDTO request, Long questionId);
}
