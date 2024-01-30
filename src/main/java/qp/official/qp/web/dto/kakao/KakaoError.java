package qp.official.qp.web.dto.kakao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class KakaoError {
    private int code;
    private String msg;
}
