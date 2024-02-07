package qp.official.qp.web.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import qp.official.qp.validation.annotation.ExistUser;

public class QuestionReportRequestDTO {

    @Getter
    public static class QuestionReportDTO{
        @ExistUser
        Long userId;
        @NotBlank
        String content;
    }


}
