package qp.official.qp.web.dto;

import java.util.List;
import lombok.Getter;

public class QuestionRequestDTO {

    @Getter
    public static class QuestionPostDTO{
        Long userId;
        String title;
        String content;
        List<String> hashtag;
    }

    @Getter
    public static class QuestionUpdateDTO{
        Long userId;
        String title;
        String content;
    }






}
