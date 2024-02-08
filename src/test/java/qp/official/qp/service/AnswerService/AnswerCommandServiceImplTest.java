package qp.official.qp.service.AnswerService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.User;
import qp.official.qp.domain.enums.AnswerLikeStatus;
import qp.official.qp.domain.enums.Category;
import qp.official.qp.domain.mapping.AnswerLikes;
import qp.official.qp.repository.AnswerLikesRepository;
import qp.official.qp.repository.AnswerRepository;
import qp.official.qp.repository.QuestionRepository;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.web.dto.AnswerRequestDTO;

@ExtendWith(MockitoExtension.class)
class AnswerCommandServiceImplTest {

    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AnswerLikesRepository answerLikesRepository;

    @InjectMocks
    private AnswerCommandServiceImpl answerCommandService;

    @Test
    @DisplayName("부모 답변 생성 테스트 코드")
    void createParentAnswer() {
        // given

        // 질문 및 유저 정보
        Long userId = 1L;
        Long questionId = 1L;
        String questionTitle = "questionTestTitle";

        // 답변 정보
        String title = "testTitle";
        String content = "testContent";
        Category category = Category.PARENT;

        Long answerId = 1L;

        // 예상 되는 답변 객체 생성
        Answer expectAnswer = Answer.builder()
            .answerId(answerId)
            .title(title)
            .content(content)
            .category(category)
            .children(new ArrayList<>())
            .build();

        // 테스트 사용자 객체 생성
        User testUser = User.builder()
            .userId(userId)
            .answerList(new ArrayList<>())
            .build();

        // 테스트 질문 객체 생성
        Question testQuestion = Question.builder()
            .questionId(questionId)
            .title(questionTitle)
            .answers(new ArrayList<>())
            .build();

        // 연관 관계 설정
        expectAnswer.setUser(testUser);
        expectAnswer.setQuestion(testQuestion);

        // userRepository.findById
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        // answerRepository.save
        when(answerRepository.save(any(Answer.class))).thenReturn(expectAnswer);

        // questionRepository.findById
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(testQuestion));

        // answerRepository.findById
        when(answerRepository.findById(answerId)).thenReturn(Optional.of(expectAnswer));

        // when
        AnswerRequestDTO.AnswerCreateDTO request = AnswerRequestDTO.AnswerCreateDTO.builder()
            .userId(userId)
            .title(title)
            .content(content)
            .category(category)
            .build();

        // 부모 질문 생성
        Answer answer = answerCommandService.createAnswer(request, questionId);



        // 자식 질문 생성
        int childAnswerSize = 3;
        Category child = Category.CHILD;

        for (int i = 0; i < childAnswerSize; i++){
            AnswerRequestDTO.AnswerCreateDTO childRequest = AnswerRequestDTO.AnswerCreateDTO.builder()
                .userId(userId)
                .title("testTitle" + i)
                .content("testContent" + i)
                .category(child)
                .answerGroup(answerId)
                .build();
            answerCommandService.createAnswer(childRequest, questionId);
        }

        // then

        // 검증
        assertEquals(answerId, answer.getAnswerId());
        assertEquals(title, answer.getTitle());
        assertEquals(content, answer.getContent());
        assertEquals(category, answer.getCategory());

        // 연관관계 검증
        assertEquals(questionId, answer.getQuestion().getQuestionId());
        assertEquals(userId, answer.getUser().getUserId());
        assertSame(answer.getQuestion().getAnswers().get(0).getAnswerId(), answer.getAnswerId());
        assertEquals(answerId, answer.getQuestion().getAnswers().get(0).getAnswerId());
        assertEquals(answerId, answer.getUser().getAnswerList().get(0).getAnswerId());

        // 자식 관계 검증
        assertEquals(childAnswerSize, answer.getChildren().size());
        for (int i = 0; i < answer.getChildren().size(); i++){
            assertEquals(answerId, answer.getChildren().get(i).getParent().getAnswerId());
        }
    }


    @Test
    @DisplayName("좋아요 추가 테스트 코드")
    void addLikeToAnswer() {
        // given

        // 질문 및 유저 정보
        Long userId = 1L;

        // 답변 정보
        String title = "testTitle";
        Long answerId = 1L;

        // 예상 답변 객체 생성
        Answer expectAnswer = Answer.builder()
            .answerId(answerId)
            .title(title)
            .build();

        // 테스트 사용자 객체 생성
        User testUser = User.builder()
            .userId(userId)
            .answerList(new ArrayList<>())
            .build();

        // 답변 좋아요 객체 생성
        Long answerLikesId = 1L;
        AnswerLikes answerLikes = AnswerLikes.builder()
            .AnswerLikeId(answerLikesId)
            .answer(expectAnswer)
            .user(testUser)
            .build();


        // userRepository.findById
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        // answerRepository.findById
        when(answerRepository.findById(answerId)).thenReturn(Optional.of(expectAnswer));

        // answerLikesRepository.save
        when(answerLikesRepository.save(any(AnswerLikes.class))).thenReturn(answerLikes);

        // answerLikesRepository.existsByAnswerAndUser
        when(answerLikesRepository.existsByAnswerAndUser(expectAnswer, testUser)).thenReturn(false);

        // when

        // 답변 좋아요 누르기
        AnswerLikeStatus answerLikeAddStatus = answerCommandService.addAndDeleteLikeToAnswer(userId, answerId);

        // then

        // 첫 생성 시에는 좋아요 추가
        assertEquals(AnswerLikeStatus.ADDED, answerLikeAddStatus);
    }
    @Test
    @DisplayName("좋아요 취소 테스트 코드")
    void deleteAnswerLike(){
        // given

        // 질문 및 유저 정보
        Long userId = 1L;

        // 답변 정보
        String title = "testTitle";
        Long answerId = 1L;

        // 예상 답변 객체 생성
        Answer expectAnswer = Answer.builder()
            .answerId(answerId)
            .title(title)
            .build();

        // 테스트 사용자 객체 생성
        User testUser = User.builder()
            .userId(userId)
            .answerList(new ArrayList<>())
            .build();

        // 이미 좋아요 있는 상태의 AnswerLikes 객체 생성
        Long alreadyAddedLikeAnswerLikeId = 2L;

        // userRepository.findById
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        // answerRepository.findById
        when(answerRepository.findById(answerId)).thenReturn(Optional.of(expectAnswer));

        // answerLikesRepository.existsByAnswerAndUser
        when(answerLikesRepository.existsByAnswerAndUser(expectAnswer, testUser)).thenReturn(true);

        // when
        AnswerLikeStatus answerLikeDeleteStatus = answerCommandService.addAndDeleteLikeToAnswer(userId, answerId);

        // then
        assertEquals(AnswerLikeStatus.DELETED, answerLikeDeleteStatus);

    }

    @Test
    void deleteAnswer() {
        // given

        // 답변 정보
        String title = "testTitle";
        Long answerId = 1L;

        // 예상 답변 객체 생성
        Answer expectAnswer = Answer.builder()
            .answerId(answerId)
            .title(title)
            .build();

        // answerRepository.findById
        when(answerRepository.findById(answerId)).thenReturn(Optional.of(expectAnswer));

        // when
        // 답변 삭제
        answerCommandService.deleteAnswer(answerId);

        // then
        // delete 메소드가 실행 되었는 지 확인
        verify(answerRepository).delete(expectAnswer);
    }

    @Test
    void updateQuestion() {
        // given
        Long answerId = 1L;
        Long userId = 1L;

        // 업데이트 전 답변 정보
        String originalTitle = "Original Title";
        String originalContent = "Original Content";

        // 업데이트 요청 정보
        String updatedTitle = "Updated Title";
        String updatedContent = "Updated Content";

        // 기존 답변 객체 생성
        Answer originalAnswer = Answer.builder()
            .answerId(answerId)
            .title(originalTitle)
            .content(originalContent)
            .build();

        // 답변 업데이트 DTO 생성
        AnswerRequestDTO.AnswerUpdateDTO request = AnswerRequestDTO.AnswerUpdateDTO.builder()
            .userId(userId)
            .title(updatedTitle)
            .content(updatedContent)
            .build();

        // answerRepository.findById
        when(answerRepository.findById(answerId)).thenReturn(Optional.of(originalAnswer));

        // when
        // 질문 업데이트
        Answer updatedAnswer = answerCommandService.updateQuestion(answerId, request);

        // then
        // answer 업데이트 확인
        assertEquals(answerId, updatedAnswer.getAnswerId());
        assertEquals(updatedTitle, updatedAnswer.getTitle());
        assertEquals(updatedContent, updatedAnswer.getContent());
    }
}