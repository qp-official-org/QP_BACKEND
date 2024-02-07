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
        @NotBlank
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
