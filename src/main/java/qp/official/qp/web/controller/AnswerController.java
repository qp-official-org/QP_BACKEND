package qp.official.qp.web.controller;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.apiPayload.code.status.SuccessStatus;
import qp.official.qp.converter.AnswerConverter;
import qp.official.qp.converter.AnswerLikesConverter;
import qp.official.qp.domain.Answer;
import qp.official.qp.domain.enums.AnswerLikeStatus;
import qp.official.qp.service.AnswerService.AnswerCommandService;
import qp.official.qp.service.AnswerService.AnswerQueryService;
import qp.official.qp.service.TokenService.TokenService;
import qp.official.qp.validation.annotation.ExistAnswer;
import qp.official.qp.validation.annotation.ExistQuestion;
import qp.official.qp.validation.annotation.ExistUser;
import qp.official.qp.web.dto.AnswerLikeResponseDTO;
import qp.official.qp.web.dto.AnswerRequestDTO;
import qp.official.qp.web.dto.AnswerResponseDTO;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/answers")
public class AnswerController {

    private final TokenService tokenService;

    private final AnswerCommandService answerCommandService;
    private final AnswerQueryService answerQueryService;

    // 답변 작성
    @PostMapping("/questions/{questionId}")
    @Operation(
            summary = "특정 질문에 대한 답변 작성 API"
            , description = "Header에 accessToken 필요. path variable로 questionId를 입력하고, Request Body에 답변을 입력하세요."
            , security = @SecurityRequirement(name = "accessToken")
    )
    public ApiResponse<AnswerResponseDTO.CreateResultDTO> createAnswer(
            @RequestBody @Valid AnswerRequestDTO.AnswerCreateDTO request,
            @PathVariable @ExistQuestion Long questionId
    ) {
        // accessToken으로 유효한 유저인지 인가
        tokenService.isValidToken(request.getUserId());

        Answer answer = answerCommandService.createAnswer(request, questionId);
        return ApiResponse.onSuccess(
                SuccessStatus.Answer_OK,
                AnswerConverter.toCreateResultDTO(answer)
        );
    }

    // 특정 질문의 부모 답변 페이징 조회
    @GetMapping("/questions/{questionId}")
    @Operation(summary = "부모 답변 페이징 조회 API",description = "path variable로 questionId를 입력하세요.")
    public ApiResponse<AnswerResponseDTO.ParentAnswerPreviewListDTO> findParentAnswerByPaging(
            @PathVariable @ExistQuestion Long questionId,
            @RequestParam @Min(0) Integer page,
            @RequestParam @Min(0) @Max(10) Integer size
    ) {
        Page<Answer> answers = answerQueryService.getAnswerListByQuestionId(
                questionId, page, size);
        return ApiResponse.onSuccess(
                SuccessStatus.Answer_OK,
                AnswerConverter.parentAnswerPreviewListDTO(answers));
    }

    // 부모 답변의 자식 답변 페이징 조회
    @GetMapping("/{parentAnswerId}")
    @Operation(summary = "자식 답변 페이징 조회 API",description = "path variable로 parentAnswerId를 입력하세요.")
    public ApiResponse<AnswerResponseDTO.ChildAnswerPreviewListDTO> findChildAnswerByPaging(
            @PathVariable @ExistAnswer Long parentAnswerId,
            @RequestParam @Min(0) Integer page,
            @RequestParam @Min(0) @Max(10) Integer size
    ) {
        Page<Answer> children = answerQueryService.getChildrenAnswersByParentAnswerId(parentAnswerId, page, size);
        return ApiResponse.onSuccess(
                SuccessStatus.Answer_OK,
                AnswerConverter.childAnswerPreviewListDTO(children)
        );
    }

    // 답변 삭제
    @DeleteMapping("/{answerId}")
    @Operation(
            summary = "답변 삭제 API"
            , description = "Header에 accessToken 필요. path variable로 삭제할 answerId를 입력하세요."
            , security = @SecurityRequirement(name = "accessToken")
    )
    public ApiResponse<?> deleteAnswer(
            @ExistAnswer @PathVariable Long answerId,
            @RequestParam("userId") @ExistUser Long userId
    ) {
        // accessToken으로 유효한 유저인지 인가
        tokenService.isValidToken(userId);

        answerCommandService.deleteAnswer(answerId);
        return ApiResponse.onSuccess(
                SuccessStatus.Answer_OK,
                null
        );
    }

    // 답변 수정
    @PatchMapping("/{answerId}")
    @Operation(
            summary = "답변 수정 API"
            , description = "Header에 accessToken 필요. path variable로 수정할 answerId를 입력하고 Reauest Body에 title과 content를 입력하세요."
            , security = @SecurityRequirement(name = "accessToken")
    )
    public ApiResponse<AnswerResponseDTO.UpdateResultDTO> updateAnswer(
            @RequestBody @Valid AnswerRequestDTO.AnswerUpdateDTO request,
            @ExistAnswer @PathVariable Long answerId
    ) {
        // accessToken으로 유효한 유저인지 인가
        tokenService.isValidToken(request.getUserId());

        return ApiResponse.onSuccess(
                SuccessStatus.Answer_OK,
                AnswerConverter.toUpdateResultDTO(
                        answerCommandService.updateQuestion(answerId, request)
                )
        );
    }

    // 답변 좋아요
    @Operation(summary = "답변 좋아요 API",
            description = "# `header`로 `accessToken`을 받아서 유효한 유저인지 확인합니다.\n" +
                    " ### 답변을 좋아요 합니다. 이미 좋아요를 누른 상태에서 다시 누르면 좋아요가 `취소`됩니다. ")
    @PostMapping("/{answerId}/users/{userId}")
    @Operation(
            summary = "답변 좋아요 API"
            , description = "Header에 accessToken 필요. path variable로 userId와 answerId를 입력하세요."
            , security = @SecurityRequirement(name = "accessToken")
    )
    public ApiResponse<AnswerLikeResponseDTO.AnswerLikesResultDTO> AnswerLike(
            @Parameter(
                    description = "좋아요를 누를 유저의 `userId`를 `path variable`로 받습니다."
            )
            @PathVariable @ExistUser Long userId,
            @Parameter(
                    description = "좋아요를 누를 답변의 `answerId`를 `path variable`로 받습니다."
            )
            @PathVariable @ExistAnswer Long answerId
    ) {
        // accessToken으로 유효한 유저인지 인가
        tokenService.isValidToken(userId);

        AnswerLikeStatus answerLikeStatus = answerCommandService.addAndDeleteLikeToAnswer(userId, answerId);

        return ApiResponse.onSuccess(
                SuccessStatus.AnswerLike_OK,
                AnswerLikesConverter.toAnswerLikesResultDTO(answerLikeStatus));
    }
}
