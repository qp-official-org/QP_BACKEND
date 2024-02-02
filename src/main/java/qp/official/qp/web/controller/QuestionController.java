package qp.official.qp.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.apiPayload.code.status.SuccessStatus;
import qp.official.qp.converter.QuestionConverter;
import qp.official.qp.domain.Question;
import qp.official.qp.service.QuestionService.QuestionCommandService;
import qp.official.qp.service.QuestionService.QuestionQueryService;
import qp.official.qp.service.TokenService.TokenService;
import qp.official.qp.validation.annotation.ExistQuestion;
import qp.official.qp.validation.annotation.ExistUser;
import qp.official.qp.web.dto.QuestionRequestDTO;
import qp.official.qp.web.dto.QuestionResponseDTO;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionCommandService questionCommandService;
    private final QuestionQueryService questionQueryService;
    private final TokenService tokenService;

    // 질문 작성
    @PostMapping
    public ApiResponse<QuestionResponseDTO.CreateResultDTO> createQuestion(
            @RequestBody @Valid QuestionRequestDTO.CreateDTO request
    ) {
        // accessToken으로 유효한 유저인지 인가

        return ApiResponse.onSuccess(
                SuccessStatus.Question_OK.getCode(),
                SuccessStatus.Question_OK.getMessage(),
                QuestionConverter.toCreateResultDTO(
                        questionCommandService.createQuestion(request)
                )
        );
    }

    // 특정 질문 조회
    @GetMapping("/{questionId}")
    public ApiResponse<QuestionResponseDTO.QuestionDTO> findQuestion(
            @PathVariable @ExistQuestion Long questionId
    ) {
        return ApiResponse.onSuccess(
                SuccessStatus.Question_OK.getCode(),
                SuccessStatus.Question_OK.getMessage(),
                QuestionConverter.toQuestionDTO(
                        questionQueryService.findById(questionId)
                )
        );
    }

    // 전체 질문 페이징 조회 (검색 가능)
    @GetMapping
    public ApiResponse<QuestionResponseDTO.QuestionPreviewListDTO> findQuestionByPaging(
            @RequestParam @Min(0) Integer page,
            @RequestParam @Min(1) @Max(10) Integer size,
            // Search
            @RequestParam(required = false) Optional<String> search

    ) {
        Page<Question> questions = questionQueryService.findAllBySearch(page, size, search);

        List<Integer> expertCounts = questionQueryService.findExpertCountByQuestion(questions);

        return ApiResponse.onSuccess(
                SuccessStatus.Question_OK.getCode(),
                SuccessStatus.Question_OK.getMessage(),
                QuestionConverter.toQuestionPreviewDTOList(
                        questions,
                        expertCounts
                )
        );
    }

    // 질문 삭제
    @DeleteMapping("/{questionId}")
    @Operation(summary = "질문 삭제 API",description = "특정 질문을 삭제하는 API입니다. path variable로 questionId를 주세요")
    public ApiResponse<?> deleteQuestion(
            @ExistQuestion @PathVariable Long questionId,
            @RequestParam("userId") @ExistUser Long userId) {
        // accessToken으로 유효한 유저인지 인가

        questionCommandService.deleteQuestion(questionId);
        return ApiResponse.onSuccess(
                SuccessStatus.Question_OK.getCode(),
                SuccessStatus.Question_OK.getMessage(),
                null
        );
    }


    // 질문 수정
    @PatchMapping("/{questionId}")
    @Operation(summary = "질문 수정 API",description = "특정 질문을 수정하는 API입니다. path variable로 questionId와 Reauest Body로 수정할 title과 content를 주세요")
    public ApiResponse<QuestionResponseDTO.QuestionUpdateResultDTO> updateQuestion(
            @RequestBody @Valid QuestionRequestDTO.UpdateDTO request,
            @ExistQuestion @PathVariable Long questionId) {
        // accessToken으로 유효한 유저인지 인가

        return ApiResponse.onSuccess(
                SuccessStatus.Question_OK.getCode(),
                SuccessStatus.Question_OK.getMessage(),
                QuestionConverter.toQuestionUpdateReturnDTO(
                        questionCommandService.updateQuestion(questionId, request)
                )
        );
    }

}
