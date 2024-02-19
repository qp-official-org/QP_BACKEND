package qp.official.qp.service.ExpertService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qp.official.qp.apiPayload.code.status.ErrorStatus;
import qp.official.qp.apiPayload.exception.handler.ExpertHandler;
import qp.official.qp.converter.ExpertConverter;
import qp.official.qp.domain.Expert;
import qp.official.qp.domain.User;
import qp.official.qp.domain.enums.Role;
import qp.official.qp.repository.ExpertRepository;
import qp.official.qp.repository.UserRepository;
import qp.official.qp.web.dto.ExpertRequestDTO;

import java.security.MessageDigest;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpertCommandServiceImpl implements ExpertCommandService {
    private final ExpertRepository expertRepository;
    private final UserRepository userRepository;
    @Override
    public Expert createAuthCode(ExpertRequestDTO.CreateAuthCodeDTO request, Long userId){
        String authCode;

        // API를 실행한 회원이 ADMIN인지 확인
        User user = userRepository.findById(userId).get();
        if(user.getRole() != Role.ADMIN){
            throw new ExpertHandler(ErrorStatus.ADMIN_UNAUTHORIZED);
        }

        // 이미 등록된 전문가일 때
        Expert existedExpert = expertRepository.findByKakaoEmail(request.getKakaoEmail());
        if(existedExpert != null){
            throw new ExpertHandler(ErrorStatus.EXPERT_EXIST);
        }

        // 해시값으로 인증코드 만들기
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        authCode = passwordEncoder.encode(request.getKakaoEmail()).substring(7,15);

        Expert expert = ExpertConverter.toExpert(request);
        expert.setAuthCode(authCode);
        expertRepository.save(expert);

        return expert;
    }

    @Override
    public void emailAuth(ExpertRequestDTO.EmailAuthDTO request, Long userId){
        String userKakaoEmail;
        String userAuthCode;
        String expertAuthCode;

        // 인증코드 일치여부 확인
        User user = userRepository.findById(userId).get();
        userKakaoEmail = user.getEmail();
        userAuthCode = request.getAuthCode();
        Expert expert = expertRepository.findByKakaoEmail(userKakaoEmail);
        if(expert == null){
            throw new ExpertHandler(ErrorStatus.EXPERT_UNAUTHORIZED);
        }
        else{
            expertAuthCode = expert.getAuthCode();
            if(!userAuthCode.equals(expertAuthCode))
                throw new ExpertHandler(ErrorStatus.AUTHCODE_NOT_MATCH);
        }
        user.updateRole(Role.EXPERT); // 전문가 인증 후 전문가로 역할 전환
        expertRepository.delete(expert); // 전문가 인증 후 Expert테이블에서 전문가 정보와 인증코드 삭제
    }
}
