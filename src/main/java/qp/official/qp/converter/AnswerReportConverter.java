package qp.official.qp.converter;

import qp.official.qp.domain.mapping.AnswerReport;
import qp.official.qp.web.dto.AnswerReportRequestDTO;
import qp.official.qp.web.dto.AnswerReportResponseDTO;

public class AnswerReportConverter {

    public static AnswerReportResponseDTO.AnswerReportResultDTO toAnswerReportResultDTO(AnswerReport answerReport){
        return AnswerReportResponseDTO.AnswerReportResultDTO.builder()
            .answerId(answerReport.getAnswer().getAnswerId())
            .userId(answerReport.getUser().getUserId())
            .answerReportId(answerReport.getAnswerReportId())
            .content(answerReport.getContent())
            .createdAt(answerReport.getCreatedAt())
            .build();
    }


    public static AnswerReport toAnswerReport(AnswerReportRequestDTO.AnswerReportDTO request){
        return AnswerReport.builder()
                .content(request.getContent())
                .build();
    }



}
