package qp.official.qp.web.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserQuestionAlarmResponseDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class UserQuestionAlarmListResultDTO{
        Long questionId;
        List<UserQuestionAlarmDTO> questionAlarms;
        Integer totalElements;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class UserQuestionAlarmDTO{
        Long userId;
        LocalDateTime createdAt;
    }

}
