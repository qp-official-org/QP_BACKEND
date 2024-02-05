package qp.official.qp.web.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import qp.official.qp.validation.annotation.ExistHashTag;
import qp.official.qp.validation.annotation.ExistUser;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class QuestionRequestDTO {

    @Getter
    public static class CreateDTO {
        @ExistUser
        Long userId;
        @NotBlank
        @Size(min = 1, max = 50)
        @NotBlank
        String title;
        @NotBlank
        @Size(min = 1, max = 1500)
        @NotBlank
        String content;

        // size = 1, max = 10
        @Size(min = 0, max = 10)
        @ExistHashTag
        List<Long> hashtag;
    }

    @Getter
    public static class UpdateDTO {
        @ExistUser
        Long userId;
        @NotBlank
        @Size(min = 1, max = 50)
        @NotBlank
        String title;
        @NotBlank
        @Size(min = 1, max = 1500)
        @NotBlank
        String content;
    }
}
