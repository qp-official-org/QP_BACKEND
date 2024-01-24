package qp.official.qp.service.QuestionService;

import org.springframework.data.domain.Page;
import qp.official.qp.domain.Question;

import java.util.List;

public interface QuestionQueryService {
    Question findById(Long questionId);

    Page<Question> findAll(int page, int size);

    List<Integer> findExpertCountByQuestion(Page<Question> questions);
}
