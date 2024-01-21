package qp.official.qp.service.QuestionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import qp.official.qp.converter.QuestionConverter;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.User;
import qp.official.qp.repository.QuestionRepository;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.web.dto.QuestionRequestDTO;

@Service
@RequiredArgsConstructor
public class QuestionCommandServiceImpl implements QuestionCommandService {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Override
    public Question createQuestion(QuestionRequestDTO.CreateDTO request) {

        Question newQuestion = QuestionConverter.toQuestion(request);

        // FindById User
        User findUser = userRepository.findById(request.getUserId()).get();

        // Set User
        newQuestion.setUser(findUser);

        // Save Question
        return questionRepository.save(newQuestion);
    }
}
