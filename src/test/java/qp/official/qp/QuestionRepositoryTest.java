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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class QuestionRepositoryTest {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final QuestionHashTagRepository questionHashTagRepository;

    @Autowired
    public QuestionRepositoryTest(
            QuestionRepository questionRepository,
            UserRepository uesrRepository,
            QuestionHashTagRepository questionHashTagRepository
    ) {
        this.questionRepository = questionRepository;
        this.userRepository = uesrRepository;
        this.questionHashTagRepository = questionHashTagRepository;
    }

    //QuestionRepository에 저장 되는지 확인, QuestionHashTagRepository에 저장 되는지 확인
    @Test
    public void saveQuestionTest() {
        //Question question = QuestionConverter.toQuestion(new QuestionRequestDTO.CreateDTO());

        //given
        //Long userId = 1L;
        String title = "testTitle";
        String content = "testContent";

//        User testUser = User.builder()
//                .userId(userId)
//                .build();
        //userRepository.save(testUser);

            //Question
        Question testQuestion = Question.builder()
                .title(title)
                .content(content)
                .build();
        //testQuestion.setUser(testUser);

            //QuestionHashtag
        Long hashtagId = 1L;
        String hashtag = "testHashtag";
            //Hashtag
        Hashtag testHashtag = Hashtag.builder()
                .hashtagId(hashtagId)
                .hashtag(hashtag)
                .build();

        Long QuestionHashtagId = 1L;
        QuestionHashTag testQuestionHashTag = QuestionHashTag.builder()
                .QuestionHashtagId(QuestionHashtagId)
                .build();
        testQuestionHashTag.setQuestion(testQuestion);
        testQuestionHashTag.setHashtag(testHashtag);

        // when
        //QuestionRepository에 저장
        questionRepository.save(testQuestion);
        questionHashTagRepository.save(testQuestionHashTag);

        // then
            //Question
        List<Question> allQuestion = questionRepository.findAll();

        assertEquals(1, allQuestion.size());
        assertEquals(title, allQuestion.stream().findAny().get().getTitle());
        assertEquals(content, allQuestion.stream().findAny().get().getContent());
//        assertEquals(testUser, allQuestion.stream().findAny().get().getUser());
//
//            //QuestionHashtag
//        List<QuestionHashTag> allQuestionHashtag = questionHashTagRepository.findAll();
//        assertEquals(1, allQuestionHashtag.size());
//        assertEquals(QuestionHashtagId, allQuestionHashtag.stream().findAny().get().getQuestionHashtagId());
//        assertEquals(testQuestion, allQuestionHashtag.stream().findAny().get().getQuestion());
//        assertEquals(testHashtag, allQuestionHashtag.stream().findAny().get().getHashtag());
    }

//    @Test
//    public void findQuestionTest() {
//        //given
//        Long userId = 1L;
//        String title = "testTitle";
//        String content = "testContent";
//
//        User testUser = User.builder()
//                .userId(userId)
//                .build();
//        //userRepository.save(testUser);
//
//        //Question
//        Question testQuestion = Question.builder()
//                .title(title)
//                .content(content)
//                .build();
//        testQuestion.setUser(testUser);
//    }
}
