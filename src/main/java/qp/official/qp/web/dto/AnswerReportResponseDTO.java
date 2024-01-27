package qp.official.qp.web.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AnswerReportResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnswerReportResultDTO {
        Long answerId;
        Long userId;
        Long answerReportId;
        String content;
        LocalDateTime createdAt;
    }

}
