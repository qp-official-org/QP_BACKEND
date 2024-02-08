package qp.official.qp.web.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

public class HashtagRequestDTO {

    @Getter
    public static class HashtagDTO{
        @NotBlank
        String hashtag;
    }


}
