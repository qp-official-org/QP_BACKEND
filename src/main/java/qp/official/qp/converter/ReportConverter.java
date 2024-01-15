package qp.official.qp.converter;

import qp.official.qp.domain.mapping.AnswerReport;
import qp.official.qp.domain.mapping.QuestionReport;
import qp.official.qp.web.dto.ReportRequestDTO;
import qp.official.qp.web.dto.ReportResponseDTO;

public class ReportConverter {

    public static ReportResponseDTO.AnswerReportReturnDTO toAnswerReportTestDTO(AnswerReport answerReport){
        return ReportResponseDTO.AnswerReportReturnDTO.builder()
            .answerId(answerReport.getAnswer().getAnswerId())
            .userId(answerReport.getUser().getUserId())
            .answerReportId(answerReport.getAnswerReportId())
            .content(answerReport.getContent())
            .createdAt(answerReport.getCreatedAt())
            .build();
    }

    public static ReportResponseDTO.QuestionReportReturnDTO toQuestionReportTestDTO(QuestionReport questionReport){
        return ReportResponseDTO.QuestionReportReturnDTO.builder()
            .questionId(questionReport.getQuestion().getQuestionId())
            .userId(questionReport.getUser().getUserId())
            .questionReportId(questionReport.getAnswerReportId())
            .content(questionReport.getContent())
            .createdAt(questionReport.getCreatedAt())
            .build();
    }

    public static AnswerReport toAnswerReport(ReportRequestDTO.AnswerReportDTO request){
        return null;
    }

    public static QuestionReport toQuestionReport(ReportRequestDTO.QuestionReportDTO request){
        return null;
    }



}
