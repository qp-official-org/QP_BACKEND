package qp.official.qp.web.dto;

import io.swagger.models.auth.In;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.domain.mapping.QuestionHashTag;

public class QuestionResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionDTO{
        Long questionId;
        Long userId;
        String profileImage;
        String title;
        Integer hit;
        Integer answerCount;
        Integer expertCount;
        List<QuestionHashTag> hashtags;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionReturnDTO{
        Long questionId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionFindReturnDTO{
        Long questionId;
        String title;
        String content;
        List<QuestionHashTag> hashtags;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionPagingTitleReturnDTO{
        List<QuestionDTO> questions;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        boolean isFirst;
        boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionUpdateReturnDTO{
        Long questionId;
        String title;
        String content;
        LocalDateTime updatedAt;
    }
}
