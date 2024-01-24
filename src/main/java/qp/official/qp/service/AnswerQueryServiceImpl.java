package qp.official.qp.service;

import java.util.List;
import java.util.NoSuchElementException;
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
@Transactional(readOnly = true)
public class AnswerQueryServiceImpl implements AnswerQueryService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Override
    public Page<Answer> getAnswerListByQuestionId(Long questionId, int page, int size) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NoSuchElementException("해당 하는 질문이 존재하지 않습니다."));
        PageRequest pageRequest = PageRequest.of(page, size);
        return answerRepository.findByQuestionOrderByCreatedAtDescAnswerIdDesc(question, pageRequest);
    }

    @Override
    public Page<Answer> getChildrenAnswersByParentAnswerId(Long parentAnswerId, int page, int size) {
        Answer parent = answerRepository.findById(parentAnswerId).orElseThrow(() -> new NoSuchElementException("해당 하는 답변이 존재하지 않습니다."));
        PageRequest pageRequest = PageRequest.of(page, size);
        return answerRepository.findByAnswerGroupOrderByCreatedAtDescAnswerIdDesc(parent.getAnswerGroup(), pageRequest);
    }
}
