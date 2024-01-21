package qp.official.qp.service.QuestionService;

import qp.official.qp.domain.Question;

public interface QuestionQueryService {
    Question findById(Long questionId);
}
