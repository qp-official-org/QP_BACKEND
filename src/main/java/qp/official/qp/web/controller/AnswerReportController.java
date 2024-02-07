package qp.official.qp.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.apiPayload.code.status.SuccessStatus;
import qp.official.qp.converter.AnswerReportConverter;
import qp.official.qp.domain.mapping.AnswerReport;
import qp.official.qp.service.AnswerReportService.AnswerReportCommandService;
import qp.official.qp.service.AnswerReportService.AnswerReportQueryService;
import qp.official.qp.service.TokenService.TokenService;
import qp.official.qp.validation.annotation.ExistAnswer;
import qp.official.qp.validation.annotation.ExistAnswerReport;
import qp.official.qp.web.dto.AnswerReportRequestDTO;
import qp.official.qp.web.dto.AnswerReportResponseDTO;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@RequestMapping("/report")
public class AnswerReportController {

    private final TokenService tokenService;

    private final AnswerReportCommandService answerReportCommandService;
    private final AnswerReportQueryService answerReportQueryService;

    // 답변에 대한 신고 작성
    @PostMapping("/answer/{answerId}")
    @Operation(summary = "답변에 대한 신고 작성 API",description = "특정 답변 또는 대댓글에 대한 신고를 작성하는 API입니다. path variable로는 신고할 asnwerId, request body로는 신고한 userId와 신고내용인 content를 주세요"
        , security = @SecurityRequirement(name = "accessToken"))
    public ApiResponse<AnswerReportResponseDTO.AnswerReportResultDTO> answerReport(
        @RequestBody @Valid AnswerReportRequestDTO.AnswerReportDTO request,
        @PathVariable @ExistAnswer Long answerId)
    {
        tokenService.isValidToken(request.getUserId());
        AnswerReport answerReport = answerReportCommandService.createAnswerReport(request, answerId);
        return ApiResponse.onSuccess(
                SuccessStatus.Report_OK,
                AnswerReportConverter.toAnswerReportResultDTO(answerReport)
        );
    }

    // 답변에 대한 신고 조회
    @GetMapping("/answer/{reportId}")
    @Operation(summary = "답변에 대한 신고 조회 API",description = "답변 또는 대댓글에 대한 특정 신고를 조회하는 API입니다. path variable로 reportId를 주세요"
        , security = @SecurityRequirement(name = "accessToken"))
    public ApiResponse<AnswerReportResponseDTO.AnswerReportResultDTO> findAnswerReport(
        @PathVariable @ExistAnswerReport Long reportId)
    {
        AnswerReport answerReport = answerReportQueryService.getAnswerReport(reportId);
        return ApiResponse.onSuccess(
                SuccessStatus.Report_OK,
                AnswerReportConverter.toAnswerReportResultDTO(answerReport)
        );
    }



}
