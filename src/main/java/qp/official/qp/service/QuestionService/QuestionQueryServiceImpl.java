package qp.official.qp.service.QuestionService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.apiPayload.exception.handler.QuestionHandler;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.enums.Role;
import qp.official.qp.domain.mapping.UserQuestionAlarm;
import qp.official.qp.repository.AnswerRepository;
import qp.official.qp.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import qp.official.qp.repository.UserQuestionAlarmRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionQueryServiceImpl implements QuestionQueryService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserQuestionAlarmRepository userQuestionAlarmRepository;

    @Override
    public Question findById(Long questionId) {
        Question question = questionRepository.findById(questionId).get();
        return questionRepository.save(question.addHit());
    }

    @Override
    public Page<Question> findAllBySearch(int page, int size, Optional<String> optSearch) {
        PageRequest request = PageRequest.of(page, size);

        // 만약 검색어가 존재한다면
        if (optSearch.isPresent()) {
            String search = optSearch.get();
            // title, content에 검색어를 포함하는 (대소문자관계없이) 질문들을 최신순으로 페이징 조회
            return questionRepository
                    .findAllByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByCreatedAtDescQuestionIdDesc(
                            search, search, request
                    );
        }
        // 검색어가 존재하지 않는다면
        // 최신순으로 페이징 조회
        return questionRepository.findAllByOrderByCreatedAtDescQuestionIdDesc(request);
    }

    @Override
    public List<Integer> findExpertCountByQuestion(Page<Question> questions) {
        return questions.stream().map(
                question -> answerRepository.countByQuestionAndUserRole(question, Role.EXPERT)
        ).collect(Collectors.toList());
    }

    @Override
    public List<UserQuestionAlarm> getUserQuestionAlarms(Long questionId) {
        Question findQuestion = questionRepository.findById(questionId).get();
        if (!userQuestionAlarmRepository.existsByQuestion(findQuestion)){
            throw new QuestionHandler(ErrorStatus.QUESTION_ALARM_NOT_FOUND);
        }
        return userQuestionAlarmRepository.findByQuestion(findQuestion);
    }

}
