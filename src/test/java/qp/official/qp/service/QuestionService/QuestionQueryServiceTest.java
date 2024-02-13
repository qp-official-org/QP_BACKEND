package qp.official.qp.service.QuestionService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.User;
import qp.official.qp.repository.HashtagRepository;
import qp.official.qp.repository.QuestionRepository;
import qp.official.qp.repository.UserRepository;

import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

// then
@ExtendWith(MockitoExtension.class)
public class QuestionQueryServiceTest {

    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private HashtagRepository hashtagRepository;

    @InjectMocks
    private QuestionQueryServiceImpl questionQueryService;
    @InjectMocks
    private QuestionCommandServiceImpl questionCommandService;

    @Test
    void getQuestion(){
        // when
        Long questionId = 1L;
        String title = "testTitle";
        String content = "testContent";
        Long userId = 1L;
        int views = 2; // 조회수

        // given
        Question question = Question.builder()
                .questionId(questionId)
                .title(title)
                .content(content)
                .user(User.builder().userId(userId).build())
                .build();

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        // 질문 조회 횟수
        for(int i = 0; i < views; i++) {
            questionQueryService.findById(questionId);
        }

        // verify
        assertEquals(question.getQuestionId(), questionId);
        assertEquals(question.getTitle(), title);
        assertEquals(question.getContent(), content);
        assertEquals(question.getUser().getUserId(), userId);
        assertEquals(question.getHit(), views);
    }

    @Test
    void getListBySearch() {
        // when
        Long questionId = 1L;
        Long questionId2 = 2L;
        String title = "testTitle";
        String title2 = "testTitle2";
        String content = "testContent";
        String content2 = "testContent2";

        int page = 0;
        int size = 10;

        // given
        Question question = Question.builder()
                .questionId(questionId)
                .title(title)
                .content(content)
                .build();

        Question question2 = Question.builder()
                .questionId(questionId2)
                .title(title2)
                .content(content2)
                .build();

        Optional<String> optSearch = Optional.of("test");
        Optional<String> optSearch2 = Optional.of("aaa");

        questionQueryService.findAllBySearch(page, size, optSearch);

        // verify
        assertTrue(question.getTitle().contains(optSearch.get()) || question.getContent().contains(optSearch.get()));
        assertTrue(question2.getTitle().contains(optSearch.get()) || question2.getContent().contains(optSearch.get()));

        assertFalse(question.getTitle().contains(optSearch2.get()) || question.getContent().contains(optSearch2.get()));
        assertFalse(question2.getTitle().contains(optSearch2.get()) || question2.getContent().contains(optSearch2.get()));
    }
}
