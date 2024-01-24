package qp.official.qp.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.apiPayload.code.status.SuccessStatus;
import qp.official.qp.converter.AnswerConverter;
import qp.official.qp.service.AnswerService.AnswerCommandService;
import qp.official.qp.service.AnswerService.AnswerQueryService;
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
    @PostMapping
    public ApiResponse<AnswerResponseDTO.AnswerCreateResultDTO> createAnswer(
            @RequestBody @Valid AnswerRequestDTO.AnswerCreateDTO request
    ){
        return ApiResponse.onSuccess(
                SuccessStatus.Answer_OK.getCode(),
                SuccessStatus.Answer_OK.getMessage(),
                AnswerConverter.toCreateResultDTO(
                        answerCommandService.createAnswer(request)
                )
        );
    }

    // 특정 질문의 부모 답변 페이징 조회
    @GetMapping(path = "/questions/{questionId}", params = {"page"})
    public ApiResponse<AnswerResponseDTO.ParentAnswerPreviewListDTO> findParentAnswerByPaging(
            @PathVariable Long questionId,
            @RequestParam Integer page
    ){
        return ApiResponse.onSuccess(
                SuccessStatus.Answer_OK.getCode(),
                SuccessStatus.Answer_OK.getMessage(),
                AnswerConverter.parentAnswerPreviewListDTO(
                        answerQueryService.getAnswerList(questionId, page)
                )
        );
    }

    // 부모 답변의 자식 답변 페이징 조회
    @GetMapping(path = "/{parentAnswerId}", params = {"page, size"})
    public ApiResponse<AnswerResponseDTO.ChildAnswerPreviewListDTO> findChildAnswerByPaging(
            @PathVariable Long parentAnswerId
    ){
        return null;
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
    public ApiResponse<AnswerResponseDTO.AnswerUpdateResultDTO> updateAnswer(
            @RequestBody AnswerRequestDTO.AnswerUpdateDTO request,
            @PathVariable Long answerId
    ){
        return null;
    }

    // 답변 좋아요
    @PostMapping("/{answerId}/users/{userId}")
    public ApiResponse<?> AnswerLike(
            @PathVariable Long userId, @PathVariable Long answerId
    ){
        return null;
    }

}
