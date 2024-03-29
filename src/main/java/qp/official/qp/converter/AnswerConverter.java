package qp.official.qp.converter;

import org.springframework.data.domain.Page;
import qp.official.qp.domain.Answer;
import qp.official.qp.web.dto.AnswerRequestDTO;
import qp.official.qp.web.dto.AnswerResponseDTO;
import java.time.LocalDateTime;
import java.util.List;
import qp.official.qp.web.dto.AnswerResponseDTO.ChildAnswerPreviewDTO;
import qp.official.qp.web.dto.AnswerResponseDTO.ChildAnswerPreviewListDTO;
import qp.official.qp.web.dto.AnswerResponseDTO.ParentAnswerPreviewDTO;

import java.util.stream.Collectors;

public class AnswerConverter {

    public static Answer toAnswer(AnswerRequestDTO.AnswerCreateDTO request){
        return Answer.builder()
                .content(request.getContent())
                .category(request.getCategory())
                .answerGroup(request.getAnswerGroup())
                .build();
    }


    public static AnswerResponseDTO.CreateResultDTO toCreateResultDTO(Answer answer){
        return AnswerResponseDTO.CreateResultDTO.builder()
                .answerId(answer.getAnswerId())
                .createdAt(LocalDateTime.now())
                .build();
    }


    public static AnswerResponseDTO.ParentAnswerPreviewListDTO parentAnswerPreviewListDTO(
        Page<Answer> parentAnswerList){

        List<AnswerResponseDTO.ParentAnswerPreviewDTO> parentAnswerPreviewDTOList = getParentAnswerPreviewDTOS(parentAnswerList);

        return AnswerResponseDTO.ParentAnswerPreviewListDTO.builder()
            .parentAnswerList(parentAnswerPreviewDTOList)
            .listSize(parentAnswerPreviewDTOList.size())
            .totalPage(parentAnswerList.getTotalPages())
            .totalElements(parentAnswerList.getTotalElements())
            .isLast(parentAnswerList.isLast())
            .isFirst(parentAnswerList.isFirst())
            .build();
    }

    private static List<ParentAnswerPreviewDTO> getParentAnswerPreviewDTOS(
        Page<Answer> parentAnswerList) {
        List<ParentAnswerPreviewDTO> parentAnswerPreviewDTOList = parentAnswerList.getContent()
            .stream()
            .map(answer -> new ParentAnswerPreviewDTO(
                UserConverter.toUserPreviewWithAnswerDTO(answer.getUser()),
                answer.getAnswerId(),
                answer.getContent(),
                answer.getCategory(),
                answer.getAnswerGroup(),
                answer.getAnswerLikesList().size(),
                answer.getChildren().size()
            ))
            .collect(Collectors.toList());
        return parentAnswerPreviewDTOList;
    }

    public static AnswerResponseDTO.ChildAnswerPreviewListDTO childAnswerPreviewListDTO(Page<Answer> childAnswerList) {
        List<ChildAnswerPreviewDTO> childAnswerDTOList = getChildAnswerPreviewDTOS(childAnswerList);

        return ChildAnswerPreviewListDTO.builder()
            .childAnswerList(childAnswerDTOList)
            .listSize(childAnswerDTOList.size())
            .totalPage(childAnswerList.getTotalPages())
            .totalElements(childAnswerList.getTotalElements())
            .isFirst(childAnswerList.isFirst())
            .isLast(childAnswerList.isLast())
            .build();
    }

    private static List<ChildAnswerPreviewDTO> getChildAnswerPreviewDTOS(Page<Answer> childAnswerList) {
        List<ChildAnswerPreviewDTO> childAnswerDTOList = childAnswerList.getContent().stream()
            .map(answer -> new ChildAnswerPreviewDTO(
                UserConverter.toUserPreviewWithAnswerDTO(answer.getUser()),
                answer.getAnswerId(),
                answer.getContent(),
                answer.getCategory(),
                answer.getAnswerGroup(),
                answer.getAnswerLikesList().size()
            ))
            .collect(Collectors.toList());
        return childAnswerDTOList;
    }


    public static AnswerResponseDTO.UpdateResultDTO toUpdateResultDTO(Answer answer) {
        return AnswerResponseDTO.UpdateResultDTO.builder()
                .answerId(answer.getAnswerId())
                .content(answer.getContent())
                .updatedAt(answer.getUpdatedAt())
                .build();
    }



}
