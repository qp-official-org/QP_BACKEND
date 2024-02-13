package qp.official.qp.web.dto;


import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import qp.official.qp.validation.annotation.ExistHashTag;
import qp.official.qp.validation.annotation.ExistUser;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class QuestionRequestDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateDTO {
        @ExistUser
        Long userId;

        @NotBlank
        @Size(min = 1, max = 50)
        String title;


        @Size(min = 1, max = 1500)
        @NotBlank
        String content;

        @NotNull
        @Size(min = 0, max = 10)
        @ExistHashTag
        List<Long> hashtag;
    }

    @Getter
    public static class UpdateDTO {
        @ExistUser
        Long userId;

        @Size(min = 1, max = 50)
        @NotBlank
        String title;

        @Size(min = 1, max = 1500)
        @NotBlank
        String content;
    }
}
