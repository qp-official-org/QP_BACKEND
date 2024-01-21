package qp.official.qp.service.QuestionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qp.official.qp.domain.Question;
import qp.official.qp.repository.QuestionRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionQueryServiceImpl implements QuestionQueryService{
    private final QuestionRepository questionRepository;
    @Override
    public Question findById(Long questionId) {
        return questionRepository.findById(questionId).get();
    }
}
