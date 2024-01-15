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
import qp.official.qp.web.dto.QuestionReportRequestDTO;
import qp.official.qp.web.dto.QuestionReportResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/report")
public class QuestionReportController {

    // 질문에 대한 신고 작성
    @PostMapping("/question/{questionId}")
    public ApiResponse<QuestionReportResponseDTO.QuestionReportReturnDTO> questionReport(
        @RequestBody QuestionReportRequestDTO.QuestionReportDTO request,
        @PathVariable Long questionId)
    {
        return null;
    }


    // 질문에 대한 신고 조회
    @GetMapping("/question/{questionId}")
    public ApiResponse<QuestionReportResponseDTO.QuestionReportReturnDTO> findQuestionReport(
        @RequestParam Long reportId,
        @PathVariable Long questionId)
    {
        return null;
    }

}
