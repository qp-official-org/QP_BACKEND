package qp.official.qp.web.dto;

import java.util.List;

import lombok.Getter;
import qp.official.qp.validation.annotation.ExistUser;

import javax.validation.constraints.Size;

public class QuestionRequestDTO {

    @Getter
    public static class CreateDTO {
        @ExistUser
        Long userId;
        @Size(min = 1, max = 50)
        String title;
        @Size(min = 1, max = 1500)
        String content;

        // size = 1, max = 10
        @Size(min = 1, max = 10)
        List<String> hashtag;
    }

    @Getter
    public static class UpdateDTO {
        Long userId;
        String title;
        String content;
    }
}
