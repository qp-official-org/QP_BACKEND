package qp.official.qp.converter;

import qp.official.qp.domain.Question;
import qp.official.qp.web.dto.QuestionRequestDTO;
import qp.official.qp.web.dto.QuestionResponseDTO;
import qp.official.qp.web.dto.QuestionResponseDTO.QuestionUpdateResultDTO;

public class QuestionConverter {

    public static Question toQuestion(QuestionRequestDTO.CreateDTO request){
        return Question.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .build();
    }

    public static Question toUpdateQuestion(QuestionRequestDTO.UpdateDTO request){
        return null;
    }

    public static QuestionResponseDTO.CreateResultDTO toCreateResultDTO(Question question){
        return QuestionResponseDTO.CreateResultDTO.builder()
            .questionId(question.getQuestionId())
            .createdAt(question.getCreatedAt())
            .build();
    }

    public static QuestionResponseDTO.QuestionPreviewDTO toQuestionPreviewDTO(Question question){
        return QuestionResponseDTO.QuestionPreviewDTO.builder()
            .questionId(question.getQuestionId())
            .title(question.getTitle())
            .content(question.getContent())
            .hashtags(question.getQuestionHashTagList())
            .createdAt(question.getCreatedAt())
            .updatedAt(question.getUpdatedAt())
            .build();
    }

    public static QuestionResponseDTO.QuestionPreviewListDTO toQuestionPagingTitleReturnDTO(){
        return null;
    }

    public static QuestionUpdateResultDTO toQuestionUpdateReturnDTO(Question question){
        return QuestionUpdateResultDTO.builder()
            .questionId(question.getQuestionId())
            .title(question.getTitle())
            .content(question.getContent())
            .updatedAt(question.getUpdatedAt())
            .build();
    }




}
