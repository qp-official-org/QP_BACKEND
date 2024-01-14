package qp.official.qp.converter;

import qp.official.qp.web.dto.QuestionResponseDTO;

public class QuestionConverter {
    public static QuestionResponseDTO.QuestionTestDTO toQuestionTestDTO(){
        return QuestionResponseDTO.QuestionTestDTO.builder()
                .testString("This is Test!")
                .build();
    }
}
