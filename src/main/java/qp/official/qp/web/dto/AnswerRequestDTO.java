package qp.official.qp.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.NoArgsConstructor;
import qp.official.qp.domain.enums.Category;
import qp.official.qp.validation.annotation.ExistUser;

public class AnswerRequestDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnswerCreateDTO {
        @NotNull
        @ExistUser
        Long userId;
        @NotBlank
        String title;
        @NotBlank
        String content;
        @NotNull(message = "카테고리는 'PARENT', 'CHILD' 중 하나여야 합니다.")
        Category category;
        Long answerGroup;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnswerUpdateDTO {
        @NotNull
        @ExistUser
        Long userId;
        @NotBlank
        String title;
        @NotBlank
        String content;
    }
}
