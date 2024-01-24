package qp.official.qp.service.AnswerService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.Question;
import qp.official.qp.repository.AnswerRepository;
import qp.official.qp.repository.QuestionRepository;

@Service
@RequiredArgsConstructor
@Transactional // <- 사용 이유
public class AnswerQueryServiceImpl implements AnswerQueryService{

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    @Override
    public Page<Answer> getAnswerList(Long QuestionId, Integer page){
        Question question = questionRepository.findById(QuestionId).get();

        Page<Answer> QuestionPage = answerRepository.findAllByQuestion(question, PageRequest.of(page, 2));
        return QuestionPage;
    }
}
