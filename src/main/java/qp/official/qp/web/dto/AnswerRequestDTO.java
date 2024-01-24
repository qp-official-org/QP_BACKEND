package qp.official.qp.web.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class AnswerRequestDTO {
    @Getter
    public static class AnswerCreateDTO {
        Long userId;
        Long questionId;
        String title;
        @NotBlank
        String content;
        Integer category;
        Long answerGroup;
    }

    @Getter
    public static class AnswerUpdateDTO {
        Long userId;
        String title;
        @NotBlank
        String content;
    }
}
