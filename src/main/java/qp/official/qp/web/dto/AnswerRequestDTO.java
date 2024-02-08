package qp.official.qp.web.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import qp.official.qp.domain.enums.Category;
import qp.official.qp.validation.annotation.ExistUser;

public class AnswerRequestDTO {
    @Getter
    public static class AnswerCreateDTO {
        @ExistUser
        Long userId;
        String title;
        @NotBlank
        String content;
        Category category;
        Long answerGroup;
    }

    @Builder
    @Getter
    public static class AnswerUpdateDTO {
        @ExistUser
        Long userId;
        String title;
        @NotBlank
        String content;
    }
}
