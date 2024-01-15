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
import qp.official.qp.web.dto.AnswerReportRequestDTO;
import qp.official.qp.web.dto.AnswerReportResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/report")
public class AnswerReportController {

    // 답변에 대한 신고 작성
    @PostMapping("/answer/{answerId}")
    public ApiResponse<AnswerReportResponseDTO.AnswerReportReturnDTO> answerReport(
        @RequestBody AnswerReportRequestDTO.AnswerReportDTO request,
        @PathVariable Long answerId)
    {
       return null;
    }

    // 답변에 대한 신고 조회
    @GetMapping("/answer/{answerId}")
    public ApiResponse<AnswerReportResponseDTO.AnswerReportReturnDTO> findAnswerReport(
        @RequestParam Long reportId,
        @PathVariable Long answerId)
    {
        return null;
    }



}
