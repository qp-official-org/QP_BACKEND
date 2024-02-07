package qp.official.qp.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.User;
import qp.official.qp.domain.mapping.AnswerLikes;

@DataJpaTest
class AnswerLikesRepositoryTest {

    private final AnswerLikesRepository answerLikesRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    private Answer answer;
    private User user;

    @Autowired
    AnswerLikesRepositoryTest(
        AnswerLikesRepository answerLikesRepository,
        AnswerRepository answerRepository,
        UserRepository userRepository){
        this.answerLikesRepository = answerLikesRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
    }


    void setUp(){
        answer = Answer.builder()
            .title("testTitle")
            .build();

        answerRepository.save(answer);

        user = User. builder()
            .nickname("testNickname")
            .email("testEmail")
            .build();

        userRepository.save(user);
    }

    @Test
    @DisplayName("existsByAnswerAndUser 메소드 테스트")
    void existsByAnswerAndUserTest(){

        // given
        setUp();

        AnswerLikes answerLikes = AnswerLikes.builder()
            .user(user)
            .answer(answer)
            .build();

        answerLikesRepository.save(answerLikes);

        // when
        boolean isExist = answerLikesRepository.existsByAnswerAndUser(answer, user);

        // then
        assertTrue(isExist);
    }

    @Test
    @DisplayName("existByAnswerAndUser 메소드 테스트")
    void deleteByAnswerAndUserTest(){
        // given
        setUp();

        AnswerLikes answerLikes = AnswerLikes.builder()
            .user(user)
            .answer(answer)
            .build();
        answerLikesRepository.save(answerLikes);

        // when
        answerLikesRepository.deleteByAnswerAndUser(answer, user);

        // then
        assertFalse(answerLikesRepository.existsByAnswerAndUser(answer, user));

    }
}