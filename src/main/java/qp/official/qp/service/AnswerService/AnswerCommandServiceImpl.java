package qp.official.qp.service.AnswerService;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qp.official.qp.converter.AnswerConverter;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.User;
import qp.official.qp.domain.enums.Category;
import qp.official.qp.repository.AnswerRepository;
import qp.official.qp.repository.QuestionRepository;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.service.AnswerService.AnswerCommandService;
import qp.official.qp.web.dto.AnswerLikeRequestDTO.AnswerLikeCreateDTO;
import qp.official.qp.web.dto.AnswerRequestDTO.AnswerCreateDTO;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AnswerCommandServiceImpl implements AnswerCommandService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Override
    public Answer createAnswer(AnswerCreateDTO request, Long questionId) {
        Answer answer = AnswerConverter.toAnswer(request);
        Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new NoSuchElementException("해당하는 질문이 존재하지 않습니다."));

        if (Category.CHILD.equals(answer.getCategory())) {
            Answer parent = answerRepository.findById(request.getAnswerGroup())
                .orElseThrow(() -> new NoSuchElementException("해당하는 답변이 존재하지 않습니다."));

            answer.setParent(parent);
            parent.setChildren(answer);
        }
        User user = userRepository.findById(request.getUserId()).get();
        answer.setUser(user);
        answer.setQuestion(question);
        return answerRepository.save(answer);
    }
}
