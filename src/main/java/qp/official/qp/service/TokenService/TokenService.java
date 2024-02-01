package qp.official.qp.service.TokenService;

import qp.official.qp.web.dto.TokenResponseDTO;

public interface TokenService {
    TokenResponseDTO createToken(Long userId);
    TokenResponseDTO isValidToken(Long userId);

    TokenResponseDTO autoSignIn(Long userId);
}
