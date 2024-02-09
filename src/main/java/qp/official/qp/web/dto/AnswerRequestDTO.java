package qp.official.qp.web.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import qp.official.qp.domain.enums.Category;
import qp.official.qp.validation.annotation.ExistUser;

public class AnswerRequestDTO {
    @Getter
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
