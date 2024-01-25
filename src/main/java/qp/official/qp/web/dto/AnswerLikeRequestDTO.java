package qp.official.qp.web.dto;

import lombok.Getter;
import qp.official.qp.validation.annotation.ExistAnswer;
import qp.official.qp.validation.annotation.ExistUser;

public class AnswerLikeRequestDTO {

    @Getter
    public static class AnswerLikeCreateDTO{
        @ExistUser
        Long userId;
        @ExistAnswer
        Long answerId;
    }
}
