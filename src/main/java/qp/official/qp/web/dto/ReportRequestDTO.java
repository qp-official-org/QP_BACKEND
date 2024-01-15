package qp.official.qp.web.dto;

import lombok.Getter;

public class ReportRequestDTO {

    @Getter
    public static class AnswerReportDTO{
        Long userId;
        Long answerId;
        String content;
    }

    @Getter
    public static class QuestionReportDTO{
        Long userId;
        Long questionId;
        String content;
    }



}
