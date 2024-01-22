package qp.official.qp.web.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class AnswerRequestDTO {
    @Getter
    public static class CreateDTO {
        Long userId;
        String title;
        @NotBlank
        String content;
        Integer category;
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
