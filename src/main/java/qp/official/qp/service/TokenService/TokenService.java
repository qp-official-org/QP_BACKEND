package qp.official.qp.service.TokenService;

public interface TokenService {
    String generateJWT(Long userId);
    boolean isValidToken(String token, Long userId);
    String getJWT();
    String generateRefreshToken(Long userId);
    String getRefreshToken(Long userId);
    boolean isExpiredRefreshToken(String refreshToken);
    String renewJWT(String refreshToken);
    boolean checkValidAndRenew(String token, Long userId);

}
