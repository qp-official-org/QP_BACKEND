package qp.official.qp.web.dto;

import lombok.Getter;

public class AnswerReportRequestDTO {

    @Getter
    public static class AnswerReportDTO{
        Long userId;
        Long answerId;
        String content;
    }




}
