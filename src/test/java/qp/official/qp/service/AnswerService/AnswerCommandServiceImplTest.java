package qp.official.qp.service.AnswerService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.User;
import qp.official.qp.domain.enums.Category;
import qp.official.qp.domain.mapping.AnswerLikes;
import qp.official.qp.repository.AnswerLikesRepository;
import qp.official.qp.repository.AnswerRepository;
import qp.official.qp.repository.QuestionRepository;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.web.dto.AnswerRequestDTO;

@ExtendWith(MockitoExtension.class)
class AnswerCommandServiceImplTest {

    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AnswerLikesRepository answerLikesRepository;

    @InjectMocks
    private AnswerCommandServiceImpl answerCommandService;

    @Test
    void createAnswer() {
        // given
        Long userId = 1L;
        Long questionId = 1L;
        String questionTitle = "questionTestTitle";

        String title = "testTitle";
        String content = "testContent";
        Category category = Category.PARENT;

        Long answerId = 1L;

        Answer expectAnswer = Answer.builder()
            .answerId(answerId)
            .title(title)
            .content(content)
            .category(category)
            .build();

        User testUser = User.builder()
            .userId(userId)
            .answerList(new ArrayList<>())
            .build();

        Question testQuestion = Question.builder()
            .questionId(questionId)
            .title(questionTitle)
            .answers(new ArrayList<>())
            .build();

        expectAnswer.setUser(testUser);
        expectAnswer.setQuestion(testQuestion);

        // userRepository.findById
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        // answerRepository.save
        when(answerRepository.save(any(Answer.class))).thenReturn(expectAnswer);

        // questionRepository.findById
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(testQuestion));

        // when
        AnswerRequestDTO.AnswerCreateDTO request = AnswerRequestDTO.AnswerCreateDTO.builder()
            .userId(userId)
            .title(title)
            .content(content)
            .category(category)
            .build();

        Answer answer = answerCommandService.createAnswer(request, questionId);

        // then

        // 검증
        assertEquals(answerId, answer.getAnswerId());
        assertEquals(title, answer.getTitle());
        assertEquals(content, answer.getContent());
        assertEquals(category, answer.getCategory());

        // 연관관계 검증
        assertEquals(questionId, answer.getQuestion().getQuestionId());
        assertEquals(userId, answer.getUser().getUserId());
        assertSame(answer.getQuestion().getAnswers().get(0).getAnswerId(), answer.getAnswerId());
        assertEquals(answerId, answer.getQuestion().getAnswers().get(0).getAnswerId());
        assertEquals(answerId, answer.getUser().getAnswerList().get(0).getAnswerId());
    }

    @Test
    void addAndDeleteLikeToAnswer() {
    }

    @Test
    void deleteAnswer() {
    }

    @Test
    void updateQuestion() {
    }
}