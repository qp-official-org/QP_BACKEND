package qp.official.qp.converter;

import qp.official.qp.domain.Expert;
import qp.official.qp.web.dto.ExpertRequestDTO;
import qp.official.qp.web.dto.ExpertResponseDTO;

public class ExpertConverter {
    public static Expert toExpert(ExpertRequestDTO.CreateAuthCodeDTO request){
        return Expert.builder()
                .name(request.getName())
                .kakaoEmail(request.getKakaoEmail())
                .build();
    }
    public static ExpertResponseDTO.CreateAuthCodeResultDTO toCreateAuthCodeResultDTO(Expert expert){
        return ExpertResponseDTO.CreateAuthCodeResultDTO.builder()
                .name(expert.getName())
                .kakaoEmail(expert.getKakaoEmail())
                .authCode(expert.getAuthCode())
                .build();
    }
}
