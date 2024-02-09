package qp.official.qp.web.dto;

import lombok.Getter;
import qp.official.qp.validation.annotation.ExistUser;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AnswerReportRequestDTO {

    @Getter
    public static class AnswerReportDTO{
        @NotNull
        @ExistUser
        Long userId;
        @NotBlank
        String content;
    }

}
