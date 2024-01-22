package qp.official.qp.service.QuestionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qp.official.qp.converter.QuestionConverter;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.User;
import qp.official.qp.domain.mapping.QuestionHashTag;
import qp.official.qp.repository.HashtagRepository;
import qp.official.qp.repository.QuestionHashTagRepository;
import qp.official.qp.repository.QuestionRepository;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.web.dto.QuestionRequestDTO;

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

        // QuestionHashTag 저장
        request.getHashtag().forEach(hashtagId -> {
            // FindById Hashtag
            Hashtag findHashTag = hashtagRepository.findById(hashtagId).get();

            // QuestionHashTag 생성
            QuestionHashTag newQuestionHashTag = QuestionHashTag.builder().build();
            // 연관관계 설정
            newQuestionHashTag.setQuestion(newQuestion);
            newQuestionHashTag.setHashtag(findHashTag);

            // Save QuestionHashTag
            questionHashTagRepository.save(newQuestionHashTag);
        });

        // FindById User
        User findUser = userRepository.findById(request.getUserId()).get();

        // 연관관계 설정
        newQuestion.setUser(findUser);

        // Question 저장
        return questionRepository.save(newQuestion);
    }
}
