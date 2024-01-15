package qp.official.qp.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.apiPayload.code.status.SuccessStatus;
import qp.official.qp.converter.ReportConverter;
import qp.official.qp.web.dto.ReportRequestDTO;
import qp.official.qp.web.dto.ReportResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/report")
public class ReportController {

    // 질문에 대한 신고 작성
    @PostMapping("/question/{questionId}")
    public ApiResponse<ReportResponseDTO.QuestionReportReturnDTO> questionReport(
        @RequestBody ReportRequestDTO.QuestionReportDTO request,
        @PathVariable Long questionId)
    {
        return null;
    }

    // 답변에 대한 신고 작성
    @PostMapping("/answer/{answerId}")
    public ApiResponse<ReportResponseDTO.AnswerReportReturnDTO> answerReport(
        @RequestBody ReportRequestDTO.AnswerReportDTO request,
        @PathVariable Long answerId)
    {
       return null;
    }

    // 질문에 대한 신고 조회
    @GetMapping("/question/{questionId}")
    public ApiResponse<ReportResponseDTO.QuestionReportReturnDTO> findQuestionReport(
        @RequestParam Long reportId,
        @PathVariable Long questionId)
    {
        return null;
    }

    // 답변에 대한 신고 조회
    @GetMapping("/answer/{answerId}")
    public ApiResponse<ReportResponseDTO.AnswerReportReturnDTO> findAnswerReport(
        @RequestParam Long reportId,
        @PathVariable Long answerId)
    {
        return null;
    }



}
