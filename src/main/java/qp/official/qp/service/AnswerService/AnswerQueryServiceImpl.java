package qp.official.qp.service.AnswerService;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.enums.Category;
import qp.official.qp.repository.AnswerRepository;
import qp.official.qp.repository.QuestionRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerQueryServiceImpl implements AnswerQueryService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    // 특정 질문의 부모 답변 페이징 조회
    @Override
    public Page<Answer> getAnswerListByQuestionId(Long questionId, int page, int size) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NoSuchElementException("해당 하는 질문이 존재하지 않습니다."));
        PageRequest pageRequest = PageRequest.of(page, size);
        return answerRepository.findByQuestionAndCategoryOrderByCreatedAtDescAnswerIdDesc(question, Category.PARENT, pageRequest);
    }

    // 부모 답변의 자식 답변 페이징 조회
    @Override
    public Page<Answer> getChildrenAnswersByParentAnswerId(Long parentAnswerId, int page, int size) {
        Answer parent = answerRepository.findById(parentAnswerId).orElseThrow(() -> new NoSuchElementException("해당 하는 답변이 존재하지 않습니다."));
        PageRequest pageRequest = PageRequest.of(page, size);
        return answerRepository.findByAnswerGroupOrderByCreatedAtDescAnswerIdDesc(parent.getAnswerId(), pageRequest);
    }
}
