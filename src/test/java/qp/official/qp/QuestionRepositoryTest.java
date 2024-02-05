package qp.official.qp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.User;
import qp.official.qp.domain.mapping.QuestionHashTag;
import qp.official.qp.repository.QuestionHashTagRepository;
import qp.official.qp.repository.QuestionRepository;
import qp.official.qp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class QuestionRepositoryTest {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Autowired
    public QuestionRepositoryTest(
            QuestionRepository questionRepository,
            UserRepository userRepository,
            QuestionHashTagRepository questionHashTagRepository
    ) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    //QuestionRepository에 저장 되는지 확인, QuestionHashTagRepository에 저장 되는지 확인
    @Test
    public void saveQuestionTest() {
        //Question question = QuestionConverter.toQuestion(new QuestionRequestDTO.CreateDTO());

        //given
        String title = "testTitle";
        String content = "testContent";

        User newUser = User.builder()
                .email("test1")
                .nickname("test1")
                .point(0L)
                .questionList(new ArrayList<>())
                .build();
        userRepository.save(newUser);

            //Question
        Question testQuestion = Question.builder()
                .title(title)
                .content(content)
                .build();
        testQuestion.setUser(newUser);

        // when
        //QuestionRepository에 저장
        questionRepository.save(testQuestion);

        // then
        List<Question> allQuestion = questionRepository.findAll();


        Question findQuestion = allQuestion.stream().findAny().get();

        assertEquals(1, allQuestion.size());
        assertEquals(title, findQuestion.getTitle());
        assertEquals(content, findQuestion.getContent());
//
            //QuestionHashtag
//        List<QuestionHashTag> allQuestionHashtag = questionHashTagRepository.findAll();
//        assertEquals(1, allQuestionHashtag.size());
//        assertEquals(QuestionHashtagId, allQuestionHashtag.stream().findAny().get().getQuestionHashtagId());
//        assertEquals(testQuestion, allQuestionHashtag.stream().findAny().get().getQuestion());
//        assertEquals(testHashtag, allQuestionHashtag.stream().findAny().get().getHashtag());
    }
}
