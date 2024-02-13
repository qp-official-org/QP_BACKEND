package qp.official.qp.service.QuestionService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.User;
import qp.official.qp.repository.HashtagRepository;
import qp.official.qp.repository.QuestionHashTagRepository;
import qp.official.qp.repository.QuestionRepository;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.web.dto.QuestionRequestDTO;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionCommandServiceTest {
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private HashtagRepository hashtagRepository;
    @Mock
    private QuestionHashTagRepository questionHashTagRepository;

    @InjectMocks
    private QuestionCommandServiceImpl questionCommandService;

    @Test
    void createQuestion() {
        //when
        Long userId = 1L;
        String title = "testTitle";
        String content = "testContent";
        ArrayList<Long> hashtagIds = new ArrayList<>();
        ArrayList<Hashtag> hashtags = new ArrayList<>();

        Long questionId = 1L;

        int hashtagSize = 2;
        for (int i = 1; i <= hashtagSize; i++) {
            Hashtag newHashTag = Hashtag.builder()
                    .hashtagId(((long) i))
                    .hashtag("test" + i)
                    .questionHashTagList(new ArrayList<>())
                    .build();
            hashtags.add(newHashTag);
        }


        Question expectQuestion = Question.builder()
                .questionId(questionId)
                .title(title)
                .content(content)
                .questionHashTagList(new ArrayList<>())
                .build();

        User testUser = User.builder()
                .userId(userId)
                .questionList(new ArrayList<>())
                .build();

        expectQuestion.setUser(testUser);


        expectQuestion.addAllHashTag(hashtags);

        // hashtagRepository.findById
        when(hashtagRepository.findAllById(hashtagIds)).thenReturn(hashtags);

        // userRepository.findById
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        // questionRepository.save
        when(questionRepository.save(any(Question.class))).thenReturn(expectQuestion);

        //given
        QuestionRequestDTO.CreateDTO request = QuestionRequestDTO.CreateDTO.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .hashtag(hashtagIds)
                .build();

        Question question = questionCommandService.createQuestion(request);

        //then
        // 검증
        assertEquals(question.getQuestionId(), questionId);
        assertEquals(question.getTitle(), title);
        assertEquals(question.getContent(), content);
        assertEquals(question.getQuestionHashTagList().size(), hashtagSize);

        // 연관관계 검증
        assertEquals(question.getUser().getUserId(), userId);
        assertEquals(question.getUser().getQuestionList().size(), hashtagSize);
        assertEquals(question.getUser().getQuestionList().get(0).getQuestionId(), questionId);
        for (int i = 0; i < question.getQuestionHashTagList().size(); i++) {
            assertEquals(question.getQuestionHashTagList().get(i).getHashtag().getHashtagId(), hashtags.get(i).getHashtagId());
        }
    }

    @Test
    void updateQuestion() {
        //when
        Long questionId = 1L;
        Long userId = 1L;
        String updateTitle = "testUpdateTitle";
        String updateContent = "testUpdateContent";

        Question expectQuestion = Question.builder()
                .questionId(questionId)
                .title(updateTitle)
                .content(updateContent)
                .questionHashTagList(new ArrayList<>())
                .build();

        User testUser = User.builder()
                .userId(userId)
                .questionList(new ArrayList<>())
                .build();

        expectQuestion.setUser(testUser);

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(expectQuestion));

        //given
        QuestionRequestDTO.UpdateDTO request = QuestionRequestDTO.UpdateDTO.builder()
                .userId(userId)
                .title(updateTitle)
                .content(updateContent)
                .build();

        Question question = questionCommandService.updateQuestion(questionId, request);

        //then
        // verify
        assertEquals(question.getQuestionId(), questionId);
        assertEquals(question.getTitle(), updateTitle);
        assertEquals(question.getContent(), updateContent);

        // 연관관계 검증
        assertEquals(question.getUser().getUserId(), userId);
        assertEquals(question.getUser().getQuestionList().get(0).getQuestionId(), questionId);

    }

    @Test
    void deleteQuestion() {
        // when
        // 질문 정보
        Long questionId = 1L;
        String title = "testTitle";
        String content = "testContent";

        //예상 질문 객체 생성
        // given
        Question expectQuestion = Question.builder()
                .questionId(questionId)
                .title(title)
                .content(content)
                .questionHashTagList(new ArrayList<>())
                .build();

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(expectQuestion));

        //질문 삭제
        questionCommandService.deleteQuestion(questionId);

        //verify
        verify(questionRepository).delete(expectQuestion);

    }





}