package qp.official.qp.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import qp.official.qp.config.JpaEnversConfiguration;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.User;
import qp.official.qp.domain.enums.Category;
import qp.official.qp.domain.enums.Role;
import qp.official.qp.web.dto.AnswerRequestDTO;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@Import(JpaEnversConfiguration.class)
public class AnswerRepositoryTest {
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final EntityManager em;

    @Autowired
    public AnswerRepositoryTest(
            AnswerRepository answerRepository,
            UserRepository userRepository,
            QuestionRepository questionRepository,
            EntityManager em
    ) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.em = em;
    }

    private static User newUser;
    private static Question testQuestion;
    private static Answer testParentAnswer;

    @BeforeEach
    void setup() {
        //User
        String email = "testEmail";
        String nickname = "testNickname";
        Long point = 0L;
        newUser = User.builder()
                .email(email)
                .nickname(nickname)
                .point(point)
                .answerList(new ArrayList<>())
                .questionList(new ArrayList<>())
                .role(Role.EXPERT)
                .build();
        userRepository.save(newUser);

        //Question
        String questionTitle = "testQuestionTitle";
        String questionContent = "testQuestionContent";
        testQuestion = Question.builder()
                .title(questionTitle)
                .content(questionContent)
                .build();
        testQuestion.setUser(newUser);
        questionRepository.save(testQuestion);

        //ParentAnswer
        String parentAnswerContent = "testParnetAnswerContent";
        Category parentAnswerCategory = Category.PARENT;
        testParentAnswer = Answer.builder()
                .content(parentAnswerContent)
                .category(parentAnswerCategory)
                .children(new ArrayList<>())
                .answerGroup(null)
                .build();
        testParentAnswer.setUser(newUser);
        testParentAnswer.setQuestion(testQuestion);
        //answerRepository.save(testParentAnswer);
    }

    //AnswerRepository에 저장 되는지 확인
    @Test
    @DisplayName("답변 생성")
    public void saveAnswerTest() {
        // given-----------------------------------------------------------------------------------------
        // static testParentAnswer 사용

        // when------------------------------------------------------------------------------------------
        //AnswerRepository에 저장
        Answer saveAnswer = answerRepository.save(testParentAnswer);

        // then------------------------------------------------------------------------------------------
        //Answer
        assertThat(saveAnswer).isNotNull();
        assertThat(saveAnswer.getContent().equals(testParentAnswer.getContent()));
        assertThat(saveAnswer.getUser().equals(testParentAnswer.getUser()));
        assertThat(saveAnswer.getUser().equals(testParentAnswer.getQuestion()));
    }

    @Test
    @DisplayName("답변 조회")
    public void findAnswerTest() {
        // given-----------------------------------------------------------------------------------------
        // static testParentAnswer 사용
        answerRepository.save(testParentAnswer);

        // when------------------------------------------------------------------------------------------
        // testParentAnswer 조회
        List<Answer> allAnswer = answerRepository.findAll();

        // then------------------------------------------------------------------------------------------
        //Answer
        assertEquals(1, allAnswer.size());
        assertEquals(testParentAnswer.getContent(), allAnswer.stream().findAny().get().getContent());
        assertEquals(newUser, allAnswer.stream().findAny().get().getUser());
        assertEquals(testQuestion, allAnswer.stream().findAny().get().getQuestion());
    }

    @Test
    @DisplayName("특정 질문의 부모 답변 페이징 조회")
    public void findPagingParentAnswerTest() {
        // given-----------------------------------------------------------------------------------------
        //Question
        answerRepository.save(testParentAnswer);
        String parentAnswerContent2 = "testParnetAnswerContent2";
        Category parentAnswerCategory = Category.PARENT;
        Answer testParentAnswer2 = Answer.builder()
                .content(parentAnswerContent2)
                .category(parentAnswerCategory)
                .children(new ArrayList<>())
                .answerGroup(null)
                .build();
        testParentAnswer2.setUser(newUser);
        testParentAnswer2.setQuestion(testQuestion);
        answerRepository.save(testParentAnswer2);

        List<Answer> list1 = answerRepository.findAll();
        list1 = list1.stream()
                .sorted(
                        Comparator.comparing(Answer::getCreatedAt)
                                .thenComparing(Answer::getAnswerId)
                                .reversed())
                .collect(Collectors.toList());

        // when------------------------------------------------------------------------------------------
        List<Answer> list2 = answerRepository.findByQuestionAndCategoryOrderByCreatedAtDescAnswerIdDesc(testQuestion, Category.PARENT, PageRequest.of(0, 10)).toList();

        // then------------------------------------------------------------------------------------------
        for (int i = 0; i < list1.size(); i++) {
            assertEquals(list1.get(i).getAnswerId(), list2.get(i).getAnswerId());
        }
    }

    @Test
    @DisplayName("부모 답변의 자식 답변 페이징 조회")
    public void findPagingChildAnswerTest() {
        // given-----------------------------------------------------------------------------------------
        answerRepository.save(testParentAnswer);
        //ChildAnswer
        String childAnswerContent = "testChildAnswerContent";
        Category childAnswerCategory = Category.CHILD;
        Answer testChildAnswer = Answer.builder()
                .content(childAnswerContent)
                .category(childAnswerCategory)
                .children(new ArrayList<>())
                .answerGroup(testParentAnswer.getAnswerId())
                .build();
        testChildAnswer.setUser(newUser);
        testChildAnswer.setQuestion(testQuestion);
        testChildAnswer.setParent(testParentAnswer);

        //ChildAnswer2
        String childnswerTitle2 = "testChildAnswerTitle2";
        String childAnswerContent2 = "testChildAnswerContent2";
        Category childAnswerCategory2 = Category.CHILD;
        Answer testChildAnswer2 = Answer.builder()
                .content(childAnswerContent2)
                .category(childAnswerCategory2)
                .children(new ArrayList<>())
                .answerGroup(testParentAnswer.getAnswerId())
                .build();
        testChildAnswer2.setUser(newUser);
        testChildAnswer2.setQuestion(testQuestion);
        testChildAnswer2.setParent(testParentAnswer);

        List<Answer> list1 = answerRepository.findAll();
        list1 = list1.stream()
                .filter(i -> i.getCategory().equals(Category.CHILD))
                .sorted(
                        Comparator.comparing(Answer::getCreatedAt)
                                .thenComparing(Answer::getAnswerId)
                                .reversed())
                .collect(Collectors.toList());

        // when------------------------------------------------------------------------------------------
        List<Answer> list2 = answerRepository.findByQuestionAndCategoryOrderByCreatedAtDescAnswerIdDesc(testQuestion, Category.CHILD, PageRequest.of(0, 10)).toList();

        // then------------------------------------------------------------------------------------------
        for (int i = 0; i < list1.size(); i++) {
            assertEquals(list1.get(i).getAnswerId(), list2.get(i).getAnswerId());
        }
    }

    @Test
    @DisplayName("답변 수정")
    public void updateAnswerTest() {
        // given-----------------------------------------------------------------------------------------
        // static testParentAnswer 사용
        // AnswerRepository에 저장
        Answer saveAnswer = answerRepository.save(testParentAnswer);

        // when------------------------------------------------------------------------------------------
        // testParentAnswer 수정
        String updateContent = "updateTestContent";
        AnswerRequestDTO.AnswerUpdateDTO request = AnswerRequestDTO.AnswerUpdateDTO.builder() // UpdateDTO에 builder annotation생성
                .content(updateContent)
                .build();

        saveAnswer.update(request);

        // then------------------------------------------------------------------------------------------
        //Answer
        List<Answer> allAnswer = answerRepository.findAll();
        assertEquals(1, allAnswer.size());
        assertEquals(updateContent, allAnswer.stream().findAny().get().getContent());
    }

    @Test
    @DisplayName("답변 삭제")
    public void deleteQuestionTest() {
        // given-----------------------------------------------------------------------------------------
        // static testParentAnswer 사용
        //ChildAnswer
        String childAnswerContent = "testChildAnswerContent";
        Category childAnswerCategory = Category.CHILD;
        Answer testChildAnswer = Answer.builder()
                .content(childAnswerContent)
                .category(childAnswerCategory)
                .children(new ArrayList<>())
                .answerGroup(testParentAnswer.getAnswerId())
                .build();
        testChildAnswer.setUser(newUser);
        testChildAnswer.setQuestion(testQuestion);
        testChildAnswer.setParent(testParentAnswer);

        //AnswerRepository에 부모답변과 자식답변 저장
        Answer saveParentAnswer = answerRepository.save(testParentAnswer);
        Answer saveChildAnswer = answerRepository.save(testChildAnswer);

        // when------------------------------------------------------------------------------------------
        //부모답변 삭제
        em.clear();
        answerRepository.delete(saveParentAnswer);
        // then------------------------------------------------------------------------------------------
        //부모답변과 자식답변 모두 삭제되는 확인
        assertFalse(answerRepository.existsById(saveParentAnswer.getAnswerId()));
        assertFalse(answerRepository.existsById(saveChildAnswer.getAnswerId()));
    }

    @Test
    @DisplayName("전문가가 작성한 답변 개수 조회")
    public void findAnswerCountByExpertTest() {
        // given-----------------------------------------------------------------------------------------
        // static testParentAnswer 사용
        //AnswerRepository에 부모답변 저장
        Answer saveParentAnswer = answerRepository.save(testParentAnswer);

        // when------------------------------------------------------------------------------------------
        // 전문가가 작성한 답변 개수 조회
        Integer count = answerRepository.countByQuestionAndUserRole(testQuestion, Role.EXPERT);
        System.out.println("count : " + count);

        // then------------------------------------------------------------------------------------------
        assertEquals(1, count);
    }
}
