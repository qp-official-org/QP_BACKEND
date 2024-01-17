package qp.official.qp.converter;

import qp.official.qp.domain.Question;
import qp.official.qp.web.dto.QuestionRequestDTO;
import qp.official.qp.web.dto.QuestionResponseDTO;
import qp.official.qp.web.dto.QuestionResponseDTO.QuestionUpdateReturnDTO;

public class QuestionConverter {

    public static Question toQuestion(QuestionRequestDTO.QuestionPostDTO request){
        return null;
    }

    public static Question toUpdateQuestion(QuestionRequestDTO.QuestionUpdateDTO request){
        return null;
    }

    public static QuestionResponseDTO.QuestionReturnDTO toQuestionReturnDTO(Question question){
        return QuestionResponseDTO.QuestionReturnDTO.builder()
            .questionId(question.getQuestionId())
            .createdAt(question.getCreatedAt())
            .build();
    }

    public static QuestionResponseDTO.QuestionFindReturnDTO toQuestionFindReturnDTO(Question question){
        return QuestionResponseDTO.QuestionFindReturnDTO.builder()
            .questionId(question.getQuestionId())
            .title(question.getTitle())
            .content(question.getContent())
            .hashtags(question.getQuestionHashTagList())
            .createdAt(question.getCreatedAt())
            .updatedAt(question.getUpdatedAt())
            .build();
    }

    public static QuestionResponseDTO.QuestionPagingTitleReturnDTO toQuestionFindByPagingReturnDTO(){
        return null;
    }

    public static QuestionResponseDTO.QuestionUpdateReturnDTO toQuestionUpdateReturnDTO(Question question){
        return QuestionUpdateReturnDTO.builder()
            .questionId(question.getQuestionId())
            .title(question.getTitle())
            .content(question.getContent())
            .updatedAt(question.getUpdatedAt())
            .build();
    }




}
