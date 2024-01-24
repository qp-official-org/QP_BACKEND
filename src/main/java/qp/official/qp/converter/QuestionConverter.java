package qp.official.qp.converter;

import org.springframework.data.domain.Page;
import qp.official.qp.domain.Hashtag;
import qp.official.qp.domain.Question;
import qp.official.qp.web.dto.QuestionRequestDTO;
import qp.official.qp.web.dto.QuestionResponseDTO;
import qp.official.qp.web.dto.QuestionResponseDTO.QuestionUpdateResultDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuestionConverter {

    public static Question toQuestion(QuestionRequestDTO.CreateDTO request) {
        return Question.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

    public static Question toQuestion(QuestionRequestDTO.UpdateDTO request) {
        return null;
    }

    public static QuestionResponseDTO.CreateResultDTO toCreateResultDTO(Question question) {
        return QuestionResponseDTO.CreateResultDTO.builder()
                .questionId(question.getQuestionId())
                .createdAt(question.getCreatedAt())
                .build();
    }

    public static QuestionResponseDTO.QuestionDTO toQuestionDTO(Question question) {

        // Get Hashtags
        List<Hashtag> hashtagList = QuestionHashtagConverter.toHashtagList(
                question.getQuestionHashTagList()
        );

        return QuestionResponseDTO.QuestionDTO.builder()
                .questionId(question.getQuestionId())
                .title(question.getTitle())
                .content(question.getContent())
                .hashtags(HashtagConverter.toHashtagResultDTOList(hashtagList))
                .createdAt(question.getCreatedAt())
                .updatedAt(question.getUpdatedAt())
                .build();
    }

    public static QuestionResponseDTO.QuestionPreviewDTO toQuestionPreviewDTO(Question question, int expertCount) {

        // Get Hashtags
        List<Hashtag> hashtagList = QuestionHashtagConverter.toHashtagList(
                question.getQuestionHashTagList()
        );

        return QuestionResponseDTO.QuestionPreviewDTO.builder()
                .user(UserConverter.toUserPreviewWithQuestionDTO(question.getUser()))
                .questionId(question.getQuestionId())
                .title(question.getTitle())
                .hit(question.getHit())
                .answerCount(question.getAnswers().size())
                .expertCount(expertCount)
                .createdAt(question.getCreatedAt())
                .updatedAt(question.getUpdatedAt())
                .hashtags(HashtagConverter.toHashtagResultDTOList(hashtagList))
                .build();
    }

    public static QuestionResponseDTO.QuestionPreviewListDTO toQuestionPreviewDTOList(
            Page<Question> questions, List<Integer> expertCountList
    ) {

        List<QuestionResponseDTO.QuestionPreviewDTO> questionPreviewDTOList = IntStream.range(0, expertCountList.size())
                .mapToObj(i -> toQuestionPreviewDTO(questions.getContent().get(i), expertCountList.get(i)))
                .collect(Collectors.toList());

        return QuestionResponseDTO.QuestionPreviewListDTO.builder()
                .questions(questionPreviewDTOList)
                .listSize(questions.getContent().size())
                .totalPage(questions.getTotalPages())
                .totalElements(questions.getTotalElements())
                .isFirst(questions.isFirst())
                .isLast(questions.isLast())
                .build();
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
