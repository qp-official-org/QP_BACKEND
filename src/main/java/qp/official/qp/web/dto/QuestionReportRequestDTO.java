package qp.official.qp.web.dto;

import lombok.Getter;

public class QuestionReportRequestDTO {

    @Getter
    public static class QuestionReportDTO{
        Long userId;
        Long questionId;
        String content;
    }


}
