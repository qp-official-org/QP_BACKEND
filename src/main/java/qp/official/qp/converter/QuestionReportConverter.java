package qp.official.qp.converter;

import qp.official.qp.domain.mapping.QuestionReport;
import qp.official.qp.web.dto.AnswerReportRequestDTO;
import qp.official.qp.web.dto.AnswerReportResponseDTO;
import qp.official.qp.web.dto.QuestionReportRequestDTO;
import qp.official.qp.web.dto.QuestionReportResponseDTO;

public class QuestionReportConverter {


    public static QuestionReportResponseDTO.QuestionReportReturnDTO toQuestionReportTestDTO(
        QuestionReport questionReport){
        return QuestionReportResponseDTO.QuestionReportReturnDTO.builder()
            .questionId(questionReport.getQuestion().getQuestionId())
            .userId(questionReport.getUser().getUserId())
            .questionReportId(questionReport.getAnswerReportId())
            .content(questionReport.getContent())
            .createdAt(questionReport.getCreatedAt())
            .build();
    }


    public static QuestionReport toQuestionReport(QuestionReportRequestDTO.QuestionReportDTO request){
        return null;
    }


}
