package qp.official.qp.web.dto.TokenDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RefreshTokenDTO {
    private String refreshToken;
    private Date expiredAt;
    private Date issuedAt;
    private Long userId;

    public boolean isExpired() {
        return new Date().after(expiredAt);
    }

    public boolean isMatchUserId(Long userId) {
        return this.userId.equals(userId);
    }
}
