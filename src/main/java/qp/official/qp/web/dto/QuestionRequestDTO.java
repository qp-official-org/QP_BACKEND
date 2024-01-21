package qp.official.qp.web.dto;

import lombok.Getter;
import qp.official.qp.validation.annotation.ExistHashTag;
import qp.official.qp.validation.annotation.ExistUser;

import javax.validation.constraints.Size;
import java.util.List;

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
        @ExistHashTag
        List<Long> hashtag;
    }

    @Getter
    public static class UpdateDTO {
        Long userId;
        String title;
        String content;
    }
}
