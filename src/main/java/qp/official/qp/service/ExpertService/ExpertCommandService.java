package qp.official.qp.service.ExpertService;

import qp.official.qp.domain.Expert;
import qp.official.qp.web.dto.ExpertRequestDTO;

public interface ExpertCommandService {
    Expert createAuthCode(ExpertRequestDTO.CreateAuthCodeDTO request, Long userId);

    void emailAuth(ExpertRequestDTO.EmailAuthDTO request, Long userId);
}
