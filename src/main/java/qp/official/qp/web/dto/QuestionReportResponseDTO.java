package qp.official.qp.web.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class QuestionReportResponseDTO {


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionReportReturnDTO{
        Long questionId;
        Long userId;
        Long questionReportId;
        String content;
        LocalDateTime createdAt;
    }
}
