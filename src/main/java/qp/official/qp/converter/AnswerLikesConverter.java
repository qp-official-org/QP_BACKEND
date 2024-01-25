package qp.official.qp.converter;

import qp.official.qp.domain.Answer;
import qp.official.qp.domain.User;
import qp.official.qp.domain.enums.AnswerLikeStatus;
import qp.official.qp.domain.mapping.AnswerLikes;
import qp.official.qp.web.dto.AnswerLikeResponseDTO;

public class AnswerLikesConverter {

    public static AnswerLikes toAnswerLike(Answer answer, User user){
        return AnswerLikes.builder()
            .answer(answer)
            .user(user)
            .build();
    }

    public static AnswerLikeResponseDTO.AnswerLikesResultDTO toAnswerLikesResultDTO(
        AnswerLikeStatus answerLikeStatus){
        return AnswerLikeResponseDTO.AnswerLikesResultDTO.builder()
            .answerLikeStatus(answerLikeStatus)
            .build();
    }

}
