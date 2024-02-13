package qp.official.qp.web.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;

public class HashtagRequestDTO {

    @Builder
    @Getter
    public static class HashtagDTO{
        @NotBlank
        String hashtag;
    }


}
