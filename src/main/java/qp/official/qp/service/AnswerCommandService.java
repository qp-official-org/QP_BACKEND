package qp.official.qp.service;

import qp.official.qp.domain.Answer;
import qp.official.qp.web.dto.AnswerRequestDTO;

public interface AnswerCommandService {
    public Answer createAnswer(AnswerRequestDTO.CreateDTO request, Long questionId);
}
