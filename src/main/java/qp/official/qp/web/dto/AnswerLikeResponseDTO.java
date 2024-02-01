package qp.official.qp.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import qp.official.qp.domain.enums.AnswerLikeStatus;

public class AnswerLikeResponseDTO {


    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AnswerLikesResultDTO{
        AnswerLikeStatus answerLikeStatus;

    }
}
