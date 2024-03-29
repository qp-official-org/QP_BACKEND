package qp.official.qp.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import qp.official.qp.domain.enums.ChildStatus;

import java.time.LocalDateTime;
import java.util.List;

public class QuestionResponseDTO {
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
    public static class QuestionDTO {
        Long questionId;
        UserResponseDTO.UserPreviewInQuestionDTO user;
        String title;
        String content;
        Long hit;
        Integer answerCount;
        Integer expertCount;
        ChildStatus childStatus;
        List<HashtagResponseDTO.HashtagReturnDTO> hashtags;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionPreviewDTO {
        UserResponseDTO.UserPreviewInQuestionDTO user;
        Long questionId;
        String title;
        Long hit;
        Integer answerCount;
        Integer expertCount;
        ChildStatus childStatus;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
        List<HashtagResponseDTO.HashtagReturnDTO> hashtags;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionPreviewListDTO {
        List<QuestionPreviewDTO> questions;
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
    public static class QuestionAdjacentDTO {
        Boolean hasLater;
        Boolean hasOlder;
        QuestionAdjacentPreviewDTO laterQuestion;
        QuestionAdjacentPreviewDTO olderQuestion;

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class QuestionAdjacentPreviewDTO {
            Long questionId;
            String title;
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionUserListDTO {
        UserResponseDTO.UserPreviewInQuestionDTO user;
        List<QuestionUserDTO> questions;
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
    public static class QuestionUserDTO {
        Long questionId;
        String title;
        Long hit;
        Integer answerCount;
        Integer expertCount;
        ChildStatus childStatus;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
        List<HashtagResponseDTO.HashtagReturnDTO> hashtags;
    }

}
