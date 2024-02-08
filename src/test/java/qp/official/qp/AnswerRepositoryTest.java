package qp.official.qp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.User;
import qp.official.qp.domain.enums.Category;
import qp.official.qp.repository.*;
import qp.official.qp.web.dto.AnswerRequestDTO;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AnswerRepositoryTest {
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public AnswerRepositoryTest(
            AnswerRepository answerRepository,
            UserRepository userRepository,
            QuestionRepository questionRepository
    ) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    private static User newUser;
    private static Question testQuestion;
    private static Answer testParentAnswer;
    private static Answer testChildAnswer;

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
                .questionList(new ArrayList<>())
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
        String parentAnswerTitle = "testParentAnswerTitle";
        String parentAnswerContent = "testParnetAnswerContent";
        Category parentAnswerCategory = Category.PARENT;
        testParentAnswer = Answer.builder()
                .title(parentAnswerTitle)
                .content(parentAnswerContent)
                .category(parentAnswerCategory)
                .answerGroup(null)
                .build();
        testParentAnswer.setUser(newUser);
        testParentAnswer.setQuestion(testQuestion);

        //ChildAnswer
        String childnswerTitle = "testChildAnswerTitle";
        String childAnswerContent = "testChildAnswerContent";
        Category childAnswerCategory = Category.CHILD;
        testChildAnswer = Answer.builder()
                .title(childnswerTitle)
                .content(childAnswerContent)
                .category(childAnswerCategory)
                .answerGroup(testParentAnswer.getAnswerId())
                .build();
        testChildAnswer.setUser(newUser);
        testChildAnswer.setQuestion(testQuestion);
        testChildAnswer.setParent(testParentAnswer);
        //testParentAnswer.setChildren(testChildAnswer);
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
        assertThat(saveAnswer.getTitle().equals(testParentAnswer.getTitle()));
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
        assertEquals(testParentAnswer.getTitle(), allAnswer.stream().findAny().get().getTitle());
        assertEquals(testParentAnswer.getContent(), allAnswer.stream().findAny().get().getContent());
        assertEquals(newUser, allAnswer.stream().findAny().get().getUser());
        assertEquals(testQuestion, allAnswer.stream().findAny().get().getQuestion());
    }

//    @Test
//    @DisplayName("질문 페이징 조회") // 1. 검색어로 조회 2. 검색어 없이 모두 조회
//    public void findQuestionByPagingTest(){
//        Question saveQuestion = questionRepository.save(testQuestion);
//        System.out.println(testQuestion.getCreatedAt() + "???");
//
//        // given-----------------------------------------------------------------------------------------
//        //Question
//        String title = "testTitle2";
//        String content = "testContent2";
//        Question testQuestion2 = Question.builder()
//                .title(title)
//                .content(content)
//                .build();
//        testQuestion2.setUser(newUser);
//
//        //Hashtag
//        String hashtag = "testHashtag2";
//        testHashtag = Hashtag.builder()
//                .hashtag(hashtag)
//                .questionHashTagList(new ArrayList<>())
//                .build();
//        hashtagRepository.save(testHashtag);
//
//        //QuestionHashTag
//        testQuestionHashTag = QuestionHashTag.builder()
//                .build();
//
//        testQuestionHashTag.setQuestion(testQuestion2);
//        testQuestionHashTag.setHashtag(testHashtag);
//
//    }

    @Test
    @DisplayName("답변 수정")
    public void updateAnswerTest() {
        // given-----------------------------------------------------------------------------------------
        // static testParentAnswer 사용
        // AnswerRepository에 저장
        Answer saveAnswer = answerRepository.save(testParentAnswer);

        // when------------------------------------------------------------------------------------------
        // testParentAnswer 수정
        String updateTitle = "updateTestTitle";
        String updateContent = "updateTestContent";
        AnswerRequestDTO.AnswerUpdateDTO request = AnswerRequestDTO.AnswerUpdateDTO.builder() // UpdateDTO에 builder annotation생성
                .title(updateTitle)
                .content(updateContent)
                .build();

        saveAnswer.update(request);

        // then------------------------------------------------------------------------------------------
        //Answer
        List<Answer> allAnswer = answerRepository.findAll();
        assertEquals(1, allAnswer.size());
        assertEquals(updateTitle, allAnswer.stream().findAny().get().getTitle());
        assertEquals(updateContent, allAnswer.stream().findAny().get().getContent());
    }

    @Test
    @DisplayName("답변 삭제")
    public void deleteQuestionTest() {
        // given-----------------------------------------------------------------------------------------
        // static testParentAnswer 사용
        //AnswerRepository에 저장
        Answer saveParentAnswer = answerRepository.save(testParentAnswer);
        //Answer saveChildAnswer = answerRepository.save(testChildAnswer); // 자기 참조?

        // when------------------------------------------------------------------------------------------
        answerRepository.delete(saveParentAnswer);

        // then------------------------------------------------------------------------------------------
        //Question
        assertFalse(answerRepository.existsById(saveParentAnswer.getAnswerId()));
        //assertFalse(answerRepository.existsById(saveChildAnswer.getAnswerId())); // 자기 참조?
    }
}
