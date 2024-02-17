package qp.official.qp.web.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import qp.official.qp.domain.enums.AnswerLikeStatus;

public class AnswerAlarmResponseDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AnswerAlarmListResultDTO{
        Long answerId;
        List<AnswerAlarmDTO> answerAlarms;
        Long totalElements;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AnswerAlarmDTO{
        Long userId;
        LocalDateTime createdAt;
    }
}
