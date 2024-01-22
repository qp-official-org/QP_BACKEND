package qp.official.qp.converter;

import qp.official.qp.domain.Hashtag;
import qp.official.qp.domain.Question;
import qp.official.qp.web.dto.HashtagResponseDTO;
import qp.official.qp.web.dto.QuestionRequestDTO;
import qp.official.qp.web.dto.QuestionResponseDTO;
import qp.official.qp.web.dto.QuestionResponseDTO.QuestionUpdateResultDTO;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionConverter {

    public static Question toQuestion(QuestionRequestDTO.CreateDTO request) {
        return Question.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

    public static Question toUpdateQuestion(QuestionRequestDTO.UpdateDTO request) {
        return null;
    }

    public static QuestionResponseDTO.CreateResultDTO toCreateResultDTO(Question question) {
        return QuestionResponseDTO.CreateResultDTO.builder()
                .questionId(question.getQuestionId())
                .createdAt(question.getCreatedAt())
                .build();
    }

    public static QuestionResponseDTO.QuestionPreviewDTO toQuestionPreviewDTO(Question question) {

        // QuestionHashTag -> Hashtag -> HashtagPreviewDTO 변환
        List<HashtagResponseDTO.HastTagPreviewDTO> hashTagList
                = question.getQuestionHashTagList().stream()
                .map(questionHashTag -> {
                    Hashtag hashtag = questionHashTag.getHashtag();
                    return HashtagConverter.toHashtagPreviewDTO(hashtag);
                }).collect(Collectors.toList());

        return QuestionResponseDTO.QuestionPreviewDTO.builder()
                .questionId(question.getQuestionId())
                .title(question.getTitle())
                .content(question.getContent())
                .hashtags(hashTagList)
                .createdAt(question.getCreatedAt())
                .modifiedAt(question.getUpdatedAt())
                .build();
    }

    public static QuestionResponseDTO.QuestionPreviewListDTO toQuestionPagingTitleReturnDTO() {
        return null;
    }

    public static QuestionUpdateResultDTO toQuestionUpdateReturnDTO(Question question) {
        return QuestionUpdateResultDTO.builder()
                .questionId(question.getQuestionId())
                .title(question.getTitle())
                .content(question.getContent())
                .updatedAt(question.getUpdatedAt())
                .build();
    }


}
