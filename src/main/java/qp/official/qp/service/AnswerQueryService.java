package qp.official.qp.service;

import org.springframework.data.domain.Page;
import qp.official.qp.domain.Answer;

public interface AnswerQueryService {
    Page<Answer> getAnswerListByQuestionId(Long questionId, int page, int size);

    Page<Answer> getChildrenAnswersByParentAnswerId(Long parentAnswerId, int page, int size);
}
