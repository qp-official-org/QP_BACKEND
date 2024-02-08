package qp.official.qp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.User;
import qp.official.qp.domain.mapping.QuestionHashTag;
import qp.official.qp.repository.HashtagRepository;
import qp.official.qp.repository.QuestionHashTagRepository;
import qp.official.qp.repository.QuestionRepository;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.web.dto.QuestionRequestDTO;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
public class QuestionRepositoryTest {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final QuestionHashTagRepository questionHashTagRepository;
    private final HashtagRepository hashtagRepository;
    private final EntityManager em;

    @Autowired
    public QuestionRepositoryTest(
            QuestionRepository questionRepository,
            UserRepository userRepository,
            QuestionHashTagRepository questionHashTagRepository,
            HashtagRepository hashtagRepository,
            EntityManager em
    ) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.questionHashTagRepository = questionHashTagRepository;
        this.hashtagRepository = hashtagRepository;
        this.em = em;
    }

    private static User newUser;
    private static Question testQuestion;
    private static Hashtag testHashtag;
    private static QuestionHashTag testQuestionHashTag;

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
        String title = "testTitle";
        String content = "testContent";
        testQuestion = Question.builder()
                .title(title)
                .content(content)
                .build();
        testQuestion.setUser(newUser);

        //Hashtag
        String hashtag = "testHashtag";
        testHashtag = Hashtag.builder()
                .hashtag(hashtag)
                .questionHashTagList(new ArrayList<>())
                .build();
        hashtagRepository.save(testHashtag);

        //QuestionHashTag
        testQuestionHashTag = QuestionHashTag.builder()
                .build();

        testQuestionHashTag.setQuestion(testQuestion);
        testQuestionHashTag.setHashtag(testHashtag);
    }

    //QuestionRepository에 저장 되는지 확인, QuestionHashTagRepository에 저장 되는지 확인
    @Test
    @DisplayName("질문 생성")
    public void saveQuestionTest() {
        //Question question = QuestionConverter.toQuestion(new QuestionRequestDTO.CreateDTO());

        // given-----------------------------------------------------------------------------------------
        // static testQuestion 사용

        // when------------------------------------------------------------------------------------------
        //QuestionRepository에 저장
        Question saveQuestion = questionRepository.save(testQuestion);
        QuestionHashTag saveQuestionHashTag = questionHashTagRepository.save(testQuestionHashTag);

        // then------------------------------------------------------------------------------------------
        //Question
        assertThat(saveQuestion).isNotNull();
        assertThat(saveQuestion.getTitle().equals(testQuestion.getTitle()));
        assertThat(saveQuestion.getContent().equals(testQuestion.getContent()));
        assertThat(saveQuestion.getUser().equals(testQuestion.getUser()));

        //QuestionHashtag
        assertThat(saveQuestionHashTag).isNotNull();
        assertThat(saveQuestionHashTag.getQuestion().equals(saveQuestionHashTag.getQuestion()));
        assertThat(saveQuestionHashTag.getHashtag().equals(saveQuestionHashTag.getHashtag()));
    }

    @Test
    @DisplayName("질문 조회")
    public void findQuestionTest() {
        // given-----------------------------------------------------------------------------------------
        // static testQuestion 사용
        questionRepository.save(testQuestion);
        questionHashTagRepository.save(testQuestionHashTag);

        // when------------------------------------------------------------------------------------------
        // testQuestion조회
        List<Question> allQuestion = questionRepository.findAll();
        List<QuestionHashTag> allQuestionHashtag = questionHashTagRepository.findAll();

        // then------------------------------------------------------------------------------------------
        //Question
        assertEquals(1, allQuestion.size());
        assertEquals(testQuestion.getTitle(), allQuestion.stream().findAny().get().getTitle());
        assertEquals(testQuestion.getContent(), allQuestion.stream().findAny().get().getContent());
        assertEquals(newUser, allQuestion.stream().findAny().get().getUser());

        //QuestionHashtag
        assertEquals(1, allQuestionHashtag.size());
        assertEquals(testQuestion, allQuestionHashtag.stream().findAny().get().getQuestion());
        assertEquals(testHashtag, allQuestionHashtag.stream().findAny().get().getHashtag());
    }

//    @Test
//    @DisplayName("질문 페이징 조회") // 1. 검색어로 조회 2. 검색어 없이 모두 조회
//    public void findQuestionByPagingTest(){
//        // given-----------------------------------------------------------------------------------------
//        //Question
//        String title2 = "testTitle2";
//        String content2 = "testContent2";
//        Question testQuestion2 = Question.builder()
//                .title(title2)
//                .content(content2)
//                .build();
//        testQuestion2.setUser(newUser);
//
//        // when------------------------------------------------------------------------------------------
//        // testQuestion조회
//        PageRequest request = PageRequest.of(1, 2);
//        Page<Question> allPageQuestion = questionRepository.findAllByOrderByCreatedAtDescQuestionIdDesc(request);
//
//        // then------------------------------------------------------------------------------------------
//        //Question
//        assertEquals(2, allPageQuestion.size());
//        assertEquals(testQuestion.getTitle(), allPageQuestion.stream().findAny().get().getTitle());
//        assertEquals(testQuestion.getContent(), allPageQuestion.stream().findAny().get().getContent());
//        assertEquals(newUser, allPageQuestion.stream().findAny().get().getUser());
//    }

    @Test
    @DisplayName("질문 수정")
    public void updateQuestionTest() {
        // given-----------------------------------------------------------------------------------------
        // static testQuestion 사용
        // QuestionRepository에 저장
        Question saveQuestion = questionRepository.save(testQuestion);

        // when------------------------------------------------------------------------------------------
        // testQuestion 수정
        String updateTitle = "updateTestTitle";
        String updateContent = "updateTestContent";
        QuestionRequestDTO.UpdateDTO request = QuestionRequestDTO.UpdateDTO.builder() // UpdateDTO에 builder annotation생성
                .title(updateTitle)
                .content(updateContent)
                .build();

        saveQuestion.update(request);

        // then------------------------------------------------------------------------------------------
        //Question
        List<Question> allQuestion = questionRepository.findAll();
        assertEquals(1, allQuestion.size());
        assertEquals(updateTitle, allQuestion.stream().findAny().get().getTitle());
        assertEquals(updateContent, allQuestion.stream().findAny().get().getContent());
    }

    @Test
    @DisplayName("질문 삭제")
    public void deleteQuestionTest() {
        // given-----------------------------------------------------------------------------------------
        // static testQuestion 사용
        //QuestionRepository에 저장
        Question saveQuestion = questionRepository.save(testQuestion);

        // when------------------------------------------------------------------------------------------
        questionRepository.delete(saveQuestion);
        em.flush();

        // then------------------------------------------------------------------------------------------
        //Question
        assertFalse(questionRepository.existsById(saveQuestion.getQuestionId()));
    }
}
