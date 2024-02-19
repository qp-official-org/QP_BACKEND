package qp.official.qp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import qp.official.qp.domain.Answer;
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

    // Create 30 dummy questions
    // Create 3 parent answers for each question
    // Create 2 child answers for each parent answer
    // 부모답변1-자식답변1, 부모답변2-자식답변2, 부모답변3 형식으로 되어 있다.
//    @Test
//    @Rollback(value = false)
//    public void createDummyQuestion() {
//        User testUser = userService.createTestUser();
//
//        // 질문 30개 생성
//        int questionSize = 30;
//        for (int i = 0; i < questionSize; i++){
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
//            // parent 답변 3개 생성
//            int parentAnswerSize = 3;
//            for(int j = 0; j < parentAnswerSize; j++){
//                String parentAnswerTitle = "parentTitle"+j;
//                String parentAnswerContent = "parentContent"+j;
//                AnswerRequestDTO.AnswerCreateDTO newAnswer = AnswerRequestDTO.AnswerCreateDTO.builder()
//                        .userId(testUser.getUserId())
//                        .title(parentAnswerTitle)
//                        .content(parentAnswerContent)
//                        .category(Category.PARENT)
//                        .build();
//                Answer parent = answerCommandService.createAnswer(newAnswer, question.getQuestionId());
//
//                // child 답변은 두 개만 생성
//                if(j < parentAnswerSize-1) {
//                    String childAnswerTitle = "childAnswerTitle";
//                    String childAnswerContent = "childAnswerContent";
//                    AnswerRequestDTO.AnswerCreateDTO newChildAnswer = AnswerRequestDTO.AnswerCreateDTO.builder()
//                            .userId(testUser.getUserId())
//                            .title(childAnswerTitle)
//                            .content(childAnswerContent)
//                            .category(Category.CHILD)
//                            .answerGroup(parent.getAnswerId())
//                            .build();
//                    answerCommandService.createAnswer(newChildAnswer, question.getQuestionId());
//                }
//
//            }
//
//        }
//
//    }

}
