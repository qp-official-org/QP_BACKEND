package qp.official.qp.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import qp.official.qp.domain.enums.Category;
import qp.official.qp.domain.enums.Role;

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
        UserResponseDTO.UserPreviewInAnswerDTO user;
        Long answerId;
        String content;
        Category category;
        Long answerGroup;
        Integer likeCount;
        Integer childAnswerCount;
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
        UserResponseDTO.UserPreviewInAnswerDTO user;
        Long answerId;
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
        String content;
        Long likeCount;
        LocalDateTime updatedAt;
    }

}
