package qp.official.qp.service.QuestionService;

import org.springframework.data.domain.Page;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.mapping.UserQuestionAlarm;

import java.util.List;
import java.util.Optional;

public interface QuestionQueryService {
    Question findById(Long questionId);

    Page<Question> findAllBySearch(int page, int size, Optional<String> optSearch);

    List<Integer> findExpertCountByQuestion(Page<Question> questions);
    List<UserQuestionAlarm> getUserQuestionAlarms(Long questionId);
    Page<Question> findUsersQuestions(Long userId, int page, int size);
    Page<Question> findAlarmedQuestions(Long userId, int page, int size);
    Integer countExpertCountByQuestion(Question question);

    Integer countExpertCountByQuestion(Question question);

    Question.QuestionAdjacent findAdjacentQuestions(Long questionId);
}
