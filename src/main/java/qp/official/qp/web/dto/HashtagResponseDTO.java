package qp.official.qp.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class HashtagResponseDTO {


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HashtagReturnDTO{
        Long hashtagId;
        String hashtag;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HastTagPreviewDTO{
        Long hashtagId;
        String hashtag;
    }
}
