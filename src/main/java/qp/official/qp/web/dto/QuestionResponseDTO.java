package qp.official.qp.web.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import qp.official.qp.domain.mapping.QuestionHashTag;

public class QuestionResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionDTO {
        Long questionId;
        UserResponseDTO.getUserInfoDTO user;
        String title;
        String content;
        List<QuestionHashTag> hashtags;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateResultDTO {
        Long questionId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionPreviewDTO {
        Long questionId;
        String title;
        String content;
        List<QuestionHashTag> hashtags;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionPreviewListDTO {
        List<QuestionDTO> questions;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        boolean isFirst;
        boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionUpdateResultDTO {
        Long questionId;
        String title;
        String content;
        LocalDateTime updatedAt;
    }
}
