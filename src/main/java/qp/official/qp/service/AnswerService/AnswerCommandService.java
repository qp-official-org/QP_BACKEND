package qp.official.qp.service.AnswerService;

import qp.official.qp.domain.Answer;
import qp.official.qp.domain.enums.AnswerLikeStatus;
import qp.official.qp.web.dto.AnswerRequestDTO;

public interface AnswerCommandService {
     Answer createAnswer(AnswerRequestDTO.AnswerCreateDTO request, Long questionId);


     AnswerLikeStatus addAndDeleteLikeToAnswer(Long userId, Long answerId);


    void deleteAnswer(Long answerId);

    Answer updateAnswer(Long answerId, AnswerRequestDTO.AnswerUpdateDTO request);

}
