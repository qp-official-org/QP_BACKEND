package qp.official.qp.web.dto;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.enums.Category;

import java.time.LocalDateTime;
import java.util.List;

public class AnswerResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateResultDTO {
        Long answerId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParentAnswerPreviewListDTO {
        List<ParentAnswerPreviewDTO> parentAnswerList;
        int listSize;
        int totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParentAnswerPreviewDTO {
        Long answerId;
        Long userId;
        String title;
        String content;
        Category category;
        Long answerGroup;
        Integer likeCount;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChildAnswerPreviewListDTO {
        List<ChildAnswerPreviewDTO> childAnswerList;
        int listSize;
        int totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChildAnswerPreviewDTO {
        Long answerId;
        Long userId;
        String title;
        String content;
        Category category;
        Long answerGroup;
        Integer likeCount;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateResultDTO {
        Long answerId;
        String title;
        String content;
        Long likeCount;
        LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetAnswerResultDTO{
        Long answerId;
        String title;
        String content;
        String category;
        Long userId;
        Long question_id;
        Long likeCount;
        boolean isParent;
        List<GetAnswerResultDTO> children;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
    }

}
