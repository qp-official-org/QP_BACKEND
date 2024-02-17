package qp.official.qp.service.AnswerService;

import java.util.List;
import org.springframework.data.domain.Page;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.mapping.AnswerAlarm;

public interface AnswerQueryService {
    Page<Answer> getAnswerListByQuestionId(Long questionId, int page, int size);

    Page<Answer> getChildrenAnswersByParentAnswerId(Long parentAnswerId, int page, int size);

    List<AnswerAlarm> getAnswerAlarms(Long answerId);
}
