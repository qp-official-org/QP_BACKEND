package qp.official.qp.web.controller;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.apiPayload.code.status.SuccessStatus;
import qp.official.qp.converter.AnswerConverter;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.mapping.AnswerLikes;
import qp.official.qp.service.AnswerService.AnswerCommandService;
import qp.official.qp.service.AnswerService.AnswerQueryService;
import qp.official.qp.validation.annotation.ExistAnswer;
import qp.official.qp.validation.annotation.ExistQuestion;
import qp.official.qp.validation.annotation.ExistUser;
import qp.official.qp.web.dto.AnswerRequestDTO;
import qp.official.qp.web.dto.AnswerResponseDTO;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/answers")
public class AnswerRestController {

    private final AnswerCommandService answerCommandService;
    private final AnswerQueryService answerQueryService;

    // 답변 작성
    @PostMapping("/questions/{questionId}")
    public ApiResponse<AnswerResponseDTO.CreateResultDTO> createAnswer(
        @RequestBody @Valid AnswerRequestDTO.AnswerCreateDTO request,
        @PathVariable @ExistQuestion Long questionId
    ){
        Answer answer = answerCommandService.createAnswer(request, questionId);
        return ApiResponse.onSuccess(
            SuccessStatus.Answer_OK.getCode(),
            SuccessStatus.Answer_OK.getMessage(),
            AnswerConverter.toCreateResultDTO(answer)
        );
    }

    // 특정 질문의 부모 답변 페이징 조회
    @GetMapping("/questions/{questionId}")
    public ApiResponse<AnswerResponseDTO.ParentAnswerPreviewListDTO> findParentAnswerByPaging(
        @PathVariable @ExistQuestion Long questionId,
        @RequestParam @Min(0) Integer page,
        @RequestParam @Min(0) @Max(10) Integer size
    ){
        Page<Answer> answers = answerQueryService.getAnswerListByQuestionId(
            questionId, page, size);
        return ApiResponse.onSuccess(
            SuccessStatus.Answer_OK.getCode(),
            SuccessStatus.Answer_OK.getMessage(),
            AnswerConverter.parentAnswerPreviewListDTO(answers));
    }

    // 부모 답변의 자식 답변 페이징 조회
    @GetMapping("/{parentAnswerId}")
    public ApiResponse<AnswerResponseDTO.ChildAnswerPreviewListDTO> findChildAnswerByPaging(
        @PathVariable @ExistAnswer Long parentAnswerId,
        @RequestParam @Min(0) Integer page,
        @RequestParam @Min(0) @Max(10) Integer size
    ){
        Page<Answer> children = answerQueryService.getChildrenAnswersByParentAnswerId(parentAnswerId, page, size);
        return ApiResponse.onSuccess(
            SuccessStatus.Answer_OK.getCode(),
            SuccessStatus.Answer_OK.getMessage(),
            AnswerConverter.childAnswerPreviewListDTO(children)
        );
    }

    // 답변 삭제
    @DeleteMapping("/{answerId}")
    public ApiResponse<?> deleteAnswer(
            @PathVariable Long answerId
    ){
        return null;
    }

    // 답변 수정
    @PatchMapping("/{answerId}")
    public ApiResponse<AnswerResponseDTO.UpdateResultDTO> updateAnswer(
            @RequestBody AnswerRequestDTO.UpdateDTO request,
            @PathVariable Long answerId
    ){
        return null;
    }

    // 답변 좋아요
    @PostMapping("/{answerId}/users/{userId}")
    public ApiResponse<?> AnswerLike(
            @PathVariable @ExistUser Long userId,
            @PathVariable @ExistAnswer Long answerId
    ){
        AnswerLikes answerLike = answerCommandService.addLikeToAnswer(userId, answerId);

        return ApiResponse.onSuccess(SuccessStatus.AnswerLike_OK.getCode(), SuccessStatus.AnswerLike_OK.getMessage(), null);
    }

}
