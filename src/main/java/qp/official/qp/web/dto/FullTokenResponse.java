package qp.official.qp.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FullTokenResponse {
    private String access_token;
    private String refresh_token;
}
