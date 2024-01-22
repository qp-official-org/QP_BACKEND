package qp.official.qp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qp.official.qp.converter.AnswerConverter;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.User;
import qp.official.qp.repository.AnswerRepository;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.web.dto.AnswerRequestDTO;

@Service
@RequiredArgsConstructor
@Transactional // <- 사용 이유
public class AnswerCommandServiceImpl implements  AnswerCommandService{
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    @Override
    public Answer createAnswer(AnswerRequestDTO.CreateDTO request) {
        Answer newAnswer = AnswerConverter.toAnswer(request);

        // FindById User
        User findUser = userRepository.findById(request.getUserId()).get();

        // 연관관계 설정
        newAnswer.setUser(findUser);

        return answerRepository.save(newAnswer);
    }
}
