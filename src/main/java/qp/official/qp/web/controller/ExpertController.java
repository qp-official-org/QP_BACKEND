package qp.official.qp.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.apiPayload.code.status.SuccessStatus;
import qp.official.qp.converter.ExpertConverter;
import qp.official.qp.domain.Expert;
import qp.official.qp.repository.ExpertRepository;
import qp.official.qp.service.ExpertService.ExpertCommandService;
import qp.official.qp.service.TokenService.TokenService;
import qp.official.qp.validation.annotation.ExistUser;
import qp.official.qp.web.dto.ExpertRequestDTO;
import qp.official.qp.web.dto.ExpertResponseDTO;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/expert")
public class ExpertController {
    private final ExpertRepository expertRepository;
    private final TokenService tokenService;
    private final ExpertCommandService expertCommandService;

    // 답변 작성
    @PostMapping("/save/{userId}")
    @Operation(
            summary = "전문가 정보 저장 및 인증번호 생성 API"
            , description = "Header에 accessToken 필요. path variable로 userId를 입력하고, Request Body에 전문가의 이름과 카카오 이메일을 입력하세요."
            , security = @SecurityRequirement(name = "accessToken")
    )
    public ApiResponse<ExpertResponseDTO.CreateAuthCodeResultDTO> createAuthCode(
            @RequestBody @Valid ExpertRequestDTO.CreateAuthCodeDTO request,
            @PathVariable @ExistUser Long userId
    ) {
        // accessToken으로 유효한 유저인지 인가
        tokenService.isValidToken(userId);

        Expert expert = expertCommandService.createAuthCode(request, userId);
        return ApiResponse.onSuccess(
                SuccessStatus.Expert_OK,
                ExpertConverter.toCreateAuthCodeResultDTO(expert)
        );
    }

    // 답변 작성
    @PostMapping("/{userId}")
    @Operation(
            summary = "입력된 인증번호로 전문가 인증하는 API"
            , description = "Header에 accessToken 필요. path variable로 userId를 입력하고, Request Body에 인증코드를 입력하세요."
            , security = @SecurityRequirement(name = "accessToken")
    )
    public ApiResponse<?> emailAuth(
            @RequestBody @Valid ExpertRequestDTO.EmailAuthDTO request,
            @PathVariable @ExistUser Long userId
    ) {
        // accessToken으로 유효한 유저인지 인가
        tokenService.isValidToken(userId);

        expertCommandService.emailAuth(request, userId);
        return ApiResponse.onSuccess(
                SuccessStatus.EXPERT_AUTHORIZED,
                null
        );
    }

}
