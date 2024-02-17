package qp.official.qp.web.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import qp.official.qp.domain.mapping.AnswerAlarm;

public class AnswerAlarmResponseDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AnswerAlarmListResultDTO{
        Long answerId;
        List<AnswerAlarmDTO> answerAlarms;
        Integer totalElements;
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
