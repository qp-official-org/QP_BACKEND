package qp.official.qp.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.apiPayload.code.status.SuccessStatus;
import qp.official.qp.converter.QuestionReportConverter;
import qp.official.qp.domain.mapping.QuestionReport;
import qp.official.qp.service.QuestionReportService.QuestionReportCommandService;
import qp.official.qp.service.QuestionReportService.QuestionReportQueryService;
import qp.official.qp.validation.annotation.ExistQuestion;
import qp.official.qp.validation.annotation.ExistQuestionReport;
import qp.official.qp.web.dto.QuestionReportRequestDTO;
import qp.official.qp.web.dto.QuestionReportResponseDTO;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@RequestMapping("/report")
public class QuestionReportController {

    private final QuestionReportCommandService questionReportCommandService;
    private final QuestionReportQueryService questionReportQueryService;

    // 질문에 대한 신고 작성
    @PostMapping("/question/{questionId}")
    @Operation(summary = "질문에 대한 신고 작성 API",description = "특정 질문에 대한 신고를 작성하는 API입니다. path variable로는 신고할 questionId, request body로는 신고한 userId와 신고내용인 content를 주세요"
        , security = @SecurityRequirement(name = "accessToken"))
    public ApiResponse<QuestionReportResponseDTO.QuestionReportResultDTO> questionReport(
        @RequestBody @Valid QuestionReportRequestDTO.QuestionReportDTO request,
        @PathVariable @ExistQuestion Long questionId)
    {
        QuestionReport questionReport = questionReportCommandService.createQuestionReport(request, questionId);
        return ApiResponse.onSuccess(
                SuccessStatus.Report_OK,
                QuestionReportConverter.toQuestionReportResultDTO(questionReport)
        );
    }


    // 질문에 대한 신고 조회
    @GetMapping("/question/{reportId}")
    @Operation(summary = "질문에 대한 신고 조회 API",description = "질문에 대한 특정 신고를 조회하는 API입니다. path variable로 reportId를 주세요"
        , security = @SecurityRequirement(name = "accessToken"))
    public ApiResponse<QuestionReportResponseDTO.QuestionReportResultDTO> findQuestionReport(
        @PathVariable @ExistQuestionReport Long reportId)
    {
        QuestionReport questionReport = questionReportQueryService.getQuestionReport(reportId);
        return ApiResponse.onSuccess(
                SuccessStatus.Report_OK,
                QuestionReportConverter.toQuestionReportResultDTO(questionReport)
        );
    }

}
