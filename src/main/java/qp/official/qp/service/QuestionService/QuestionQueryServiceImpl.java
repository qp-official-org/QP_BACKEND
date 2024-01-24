package qp.official.qp.service.QuestionService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.enums.Role;
import qp.official.qp.repository.AnswerRepository;
import qp.official.qp.repository.QuestionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionQueryServiceImpl implements QuestionQueryService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Override
    public Question findById(Long questionId) {
        return questionRepository.findById(questionId).get();
    }

    @Override
    public Page<Question> findAll(int page, int size) {

        PageRequest request = PageRequest.of(page, size);

        return questionRepository.findAllByOrderByCreatedAtDescQuestionIdDesc(request);
    }

    @Override
    public List<Integer> findExpertCountByQuestion(Page<Question> questions) {
        return questions.stream().map(
                question -> answerRepository.countByQuestionAndUserRole(question, Role.EXPERT)
        ).collect(Collectors.toList());
    }
}
