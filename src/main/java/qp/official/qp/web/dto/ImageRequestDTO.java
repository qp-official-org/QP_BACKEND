package qp.official.qp.web.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

public class ImageRequestDTO {

    @Getter
    public static class ImageDTO{

        @NotBlank
        String url;
    }
}
