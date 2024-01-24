package qp.official.qp.web.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import qp.official.qp.domain.enums.Category;

public class AnswerRequestDTO {
    @Getter
    public static class CreateDTO {
        Long userId;
        String title;
        @NotBlank
        String content;
        Category category;
        Long answerGroup;
    }

    @Getter
    public static class UpdateDTO {
        Long userId;
        String title;
        @NotBlank
        String content;
    }
}
