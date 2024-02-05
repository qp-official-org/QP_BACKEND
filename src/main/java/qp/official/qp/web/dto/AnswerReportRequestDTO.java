package qp.official.qp.web.dto;

import lombok.Getter;
import qp.official.qp.validation.annotation.ExistUser;

import javax.validation.constraints.NotBlank;

public class AnswerReportRequestDTO {

    @Getter
    public static class AnswerReportDTO{
        @ExistUser
        Long userId;
        @NotBlank
        String content;
    }

}
