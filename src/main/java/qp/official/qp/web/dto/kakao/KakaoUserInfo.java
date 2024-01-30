package qp.official.qp.web.dto.kakao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class KakaoUserInfo {
    @NotNull
    private Long id;
    @NotNull
    private String expires_in;
    @NotNull
    private int app_id;
    @Setter
    private String refreshToken;
}
