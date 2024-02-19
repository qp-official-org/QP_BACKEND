package qp.official.qp.service.AnswerService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.enums.Category;
import qp.official.qp.repository.AnswerRepository;
import qp.official.qp.repository.QuestionRepository;

@ExtendWith(MockitoExtension.class)
class AnswerQueryServiceImplTest {

    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private AnswerQueryServiceImpl answerQueryService;

    @Test
    void getAnswerListByQuestionId(){
        // given
        Long questionId = 1L;
        int page = 0;
        int size = 10;

        String questionTitle = "test";

        // 질문 객체 생성
        Question expectedQuestion = Question.builder()
            .title(questionTitle)
            .answers(new ArrayList<>())
            .build();

        // 예상 답변 리스트 생성
        int expectedAnswerListSize = 3;

        List<Answer> expectedAnswerList = new ArrayList<>();
        for (int i = 1; i <= expectedAnswerListSize; i++) {
            expectedAnswerList.add(Answer.builder()
                .answerId((long) i)
                .content("Test" + i)
                .build());
        }

        // 페이지 객체 생성
        Page<Answer> expectedPage = new PageImpl<>(expectedAnswerList);
        PageRequest pageRequest = PageRequest.of(page, size);

        // questionRepository.findById
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(expectedQuestion));

        // answerRepository.findByQuestionAndCategoryOrderByCreatedAtDescAnswerIdDesc
        when(answerRepository.findByQuestionAndCategoryOrderByCreatedAtDescAnswerIdDesc(expectedQuestion, Category.PARENT, pageRequest)).thenReturn(expectedPage);

        // when
        Page<Answer> answers = answerQueryService.getAnswerListByQuestionId(questionId, page, size);

        // then

        // 사이즈 비교
        assertEquals(expectedAnswerListSize, answers.getSize());
        // 페이지 내용 비교
        assertEquals(expectedPage.getContent(), answers.getContent());
        // 전체 페이지 비교
        assertEquals(expectedPage.getTotalPages(), answers.getTotalPages());
        // 전체 요소 수 비교
        assertEquals(expectedPage.getTotalElements(), answers.getTotalElements());
    }

    @Test
    void getChildrenAnswersByParentAnswerId(){
        // given

        // given
        Long parentAnswerId = 1L;
        int page = 0;
        int size = 10;

        String questionTitle = "test";
        String answerContent = "testAnswerContent";

        // 부모답변 객체 생성
        Answer parentAnswer = Answer.builder()
            .answerId(parentAnswerId)
            .content(answerContent)
            .children(new ArrayList<>())
            .build();

        // 예상 답변 리스트 생성
        int expectedChildAnswerListSize = 3;

        List<Answer> parentAnswerList = new ArrayList<>();

        for (int i = 1; i <= expectedChildAnswerListSize; i++) {
            parentAnswerList.add(Answer.builder()
                .answerId((long) i)
                .content("Test" + i)
                .category(Category.CHILD)
                .parent(parentAnswer)
                .answerGroup(parentAnswerId)
                .build());
        }
        // 페이지 객체 생성
        Page<Answer> expectedPage = new PageImpl<>(parentAnswerList);
        PageRequest pageRequest = PageRequest.of(page, size);

        // questionRepository.findById
        when(answerRepository.findById(parentAnswerId)).thenReturn(Optional.ofNullable(parentAnswer));

        // answerRepository.findByAnswerGroupOrderByCreatedAtDescAnswerIdDesc
        when(answerRepository.findByAnswerGroupOrderByCreatedAtDescAnswerIdDesc(parentAnswerId, pageRequest)).thenReturn(expectedPage);

        // when
        Page<Answer> answers = answerQueryService.getChildrenAnswersByParentAnswerId(parentAnswerId, page, size);

        // then

        // 사이즈 비교
        assertEquals(expectedChildAnswerListSize, answers.getSize());
        // 페이지 내용 비교
        assertEquals(parentAnswerList, answers.getContent());
        // 전체 페이지 비교
        assertEquals(expectedPage.getTotalPages(), answers.getTotalPages());
        // 전체 요소 수 비교
        assertEquals(expectedPage.getTotalElements(), answers.getTotalElements());
    }

}