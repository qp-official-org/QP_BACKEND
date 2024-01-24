package qp.official.qp.converter;

import qp.official.qp.domain.Answer;
import qp.official.qp.domain.enums.Category;
import qp.official.qp.web.dto.AnswerRequestDTO;
import qp.official.qp.web.dto.AnswerResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public class AnswerConverter {

    // 클라이언트의 요청 데이터를 JPA에서 처리하기 위한 DTO to Entity

    public static Answer toAnswer(AnswerRequestDTO.CreateDTO request){

        Category category = null;

        switch (request.getCategory()) {
            case 1:
                category = Category.PARENT;
                break;
            case 2:
                category = Category.CHILD;
                break;
        }

        return Answer.builder()
                // userId 처리는 어떻게?
                .title(request.getTitle())
                .content(request.getContent())
                .category(category)
                .answerGroup(request.getAnswerGroup())
                .build();
    }

    public static Answer toAnswer(AnswerRequestDTO.UpdateDTO request){
        return Answer.builder()
                // userId 처리는 어떻게?
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }


    // 응답을 위한 Entity to DTO

    public static AnswerResponseDTO.CreateResultDTO toCreateResultDTO(Answer answer){
        return AnswerResponseDTO.CreateResultDTO.builder()
                .answerId(answer.getAnswerId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AnswerResponseDTO.ParentAnswerPreviewDTO parentAnswerPreviewDTO(Answer answer){
        return null;
    }

    public static AnswerResponseDTO.ParentAnswerPreviewListDTO parentAnswerPreviewListDTO(List<Answer> parentAnswerList){
        return null;
    }

    public static AnswerResponseDTO.ChildAnswerPreviewDTO childAnswerPreviewDTO(Answer answer){
        return null;
    }

    public static AnswerResponseDTO.ChildAnswerPreviewListDTO childAnswerPreviewListDTO(List<Answer> childAnswerList){
        return null;
    }

}