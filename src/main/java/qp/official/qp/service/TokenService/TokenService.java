package qp.official.qp.service.TokenService;

import qp.official.qp.web.dto.TokenDTO.TokenResponseDTO;

public interface TokenService {
    TokenResponseDTO createToken(Long userId);
    TokenResponseDTO isValidToken(Long userId);
    TokenResponseDTO autoSignIn(Long userId);
}
