package qp.official.qp.service.AnswerService;

import org.springframework.data.domain.Page;
import qp.official.qp.domain.Answer;

public interface AnswerQueryService {

    Page<Answer> getAnswerList(Long QuestionId, Integer page);
}
