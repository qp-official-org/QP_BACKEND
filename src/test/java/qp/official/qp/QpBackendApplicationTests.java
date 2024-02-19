package qp.official.qp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.User;
import qp.official.qp.domain.enums.Category;
import qp.official.qp.domain.enums.ChildStatus;
import qp.official.qp.service.AnswerService.AnswerCommandServiceImpl;
import qp.official.qp.service.QuestionService.QuestionCommandServiceImpl;
import qp.official.qp.service.UserService.UserServiceImpl;
import qp.official.qp.web.dto.AnswerRequestDTO;
import qp.official.qp.web.dto.QuestionRequestDTO;

import java.util.ArrayList;

@SpringBootTest
class QpBackendApplicationTests {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private QuestionCommandServiceImpl questionCommandService;

    @Autowired
    private AnswerCommandServiceImpl answerCommandService;

    @Test
    void contextLoads() {
    }

    // Create 100 dummy questions
//    @Test
//    @Rollback(value = false)
//    public void createDummyQuestion() {
//        User testUser = userService.createTestUser();
//
//        for (int i = 0; i < 100; i++){
//            String title = "title" + i;
//            String content = "content" + i;
//            ArrayList<Long> hashtagIds = new ArrayList<>();
//            ArrayList<Hashtag> hashtags = new ArrayList<>();
//
//            int hashtagSize = 2;
//            for (int j = 1; j <= hashtagSize; j++) {
//                Hashtag newHashTag = Hashtag.builder()
//                        .hashtagId(((long) j))
//                        .hashtag("hashtag" + j)
//                        .questionHashTagList(new ArrayList<>())
//                        .build();
//                hashtags.add(newHashTag);
//            }
//
//            QuestionRequestDTO.CreateDTO newQuestion = QuestionRequestDTO.CreateDTO.builder()
//                    .userId(testUser.getUserId())
//                    .title(title)
//                    .content(content)
//                    .childStatus(ChildStatus.INACTIVE)
//                    .hashtag(hashtagIds)
//                    .build();
//            Question question = questionCommandService.createQuestion(newQuestion);
//
//            String testAnswerTitle = "testAnswerTitle"+i;
//            String testAnswerContent = "testAnswerContent"+i;
//            AnswerRequestDTO.AnswerCreateDTO newAnswer = AnswerRequestDTO.AnswerCreateDTO.builder()
//                    .userId(testUser.getUserId())
//                    .title(testAnswerTitle)
//                    .content(testAnswerContent)
//                    .category(Category.PARENT)
//                    .build();
//
//            answerCommandService.createAnswer(newAnswer, question.getQuestionId());
//
//        }
//
//    }

}
