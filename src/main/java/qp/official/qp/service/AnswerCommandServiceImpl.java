package qp.official.qp.service;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qp.official.qp.converter.AnswerConverter;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.User;
import qp.official.qp.domain.enums.Category;
import qp.official.qp.repository.AnswerRepository;
import qp.official.qp.repository.QuestionRepository;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.web.dto.AnswerRequestDTO;
import qp.official.qp.web.dto.AnswerRequestDTO.CreateDTO;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AnswerCommandServiceImpl implements AnswerCommandService{

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;


    @Override
    public Answer createAnswer(CreateDTO request) {
        return null;
    }
}
