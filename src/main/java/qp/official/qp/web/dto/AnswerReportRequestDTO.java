package qp.official.qp.web.dto;

import lombok.Getter;
import qp.official.qp.validation.annotation.ExistUser;

public class AnswerReportRequestDTO {

    @Getter
    public static class AnswerReportDTO{
        @ExistUser
        Long userId;
        String content;
    }

}
