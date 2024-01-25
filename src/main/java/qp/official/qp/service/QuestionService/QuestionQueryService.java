package qp.official.qp.service.QuestionService;

import org.springframework.data.domain.Page;
import qp.official.qp.domain.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionQueryService {
    Question findById(Long questionId);

    Page<Question> findAllBySearch(int page, int size, Optional<String> optSearch);

    List<Integer> findExpertCountByQuestion(Page<Question> questions);
}
