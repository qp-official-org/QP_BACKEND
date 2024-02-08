package qp.official.qp.service.QuestionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qp.official.qp.converter.QuestionConverter;
import qp.official.qp.converter.QuestionHashtagConverter;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.User;
import qp.official.qp.domain.mapping.QuestionHashTag;
import qp.official.qp.repository.HashtagRepository;
import qp.official.qp.repository.QuestionHashTagRepository;
import qp.official.qp.repository.QuestionRepository;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.web.dto.QuestionRequestDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionCommandServiceImpl implements QuestionCommandService {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;
    private final QuestionHashTagRepository questionHashTagRepository;

    @Override
    public Question createQuestion(QuestionRequestDTO.CreateDTO request) {

        // Question 생성
        Question newQuestion = QuestionConverter.toQuestion(request);

        // HashTag List 조회
        List<Hashtag> hashtagList = hashtagRepository.findAllById(request.getHashtag());

        // QuestionHashTag 생성
        List<QuestionHashTag> questionHashTagList = QuestionHashtagConverter.toQuestionHashTagList(newQuestion, hashtagList);

        // QuestionHashTag 저장
        questionHashTagRepository.saveAll(questionHashTagList);

        // FindById User
        User findUser = userRepository.findById(request.getUserId()).get();

        // 연관관계 설정
        newQuestion.setUser(findUser);

        // Question 저장
        return questionRepository.save(newQuestion);
    }

    @Override
    public Question updateQuestion(Long questionId, QuestionRequestDTO.UpdateDTO request) {
        // FindById Question
        Question updateQuestion = questionRepository.findById(questionId).get();
        updateQuestion.update(request);

        return updateQuestion;
    }

    @Override
    public void deleteQuestion(Long questionId) {
        Question deleteQuestion = questionRepository.findById(questionId).get();
        questionRepository.delete(deleteQuestion);
    }
}
