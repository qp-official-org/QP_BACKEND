package qp.official.qp.web.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenResponseDTO {
    String accessToken;
    String refreshToken;
}
