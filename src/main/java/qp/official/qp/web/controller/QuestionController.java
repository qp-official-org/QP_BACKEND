package qp.official.qp.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.apiPayload.code.status.SuccessStatus;
import qp.official.qp.converter.QuestionConverter;
import qp.official.qp.domain.Question;
import qp.official.qp.domain.mapping.UserQuestionAlarm;
import qp.official.qp.service.QuestionService.QuestionCommandService;
import qp.official.qp.service.QuestionService.QuestionQueryService;
import qp.official.qp.service.TokenService.TokenService;
import qp.official.qp.validation.annotation.ExistAnswer;
import qp.official.qp.validation.annotation.ExistQuestion;
import qp.official.qp.validation.annotation.ExistUser;
import qp.official.qp.web.dto.QuestionRequestDTO;
import qp.official.qp.web.dto.QuestionResponseDTO;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
import qp.official.qp.web.dto.UserQuestionAlarmResponseDTO.UserQuestionAlarmDTO;
import qp.official.qp.web.dto.UserQuestionAlarmResponseDTO.UserQuestionAlarmListResultDTO;

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
    @Operation(
            summary = "질문 작성 API"
            , description = "Header에 accessToken 필요. Request Body에 생성할 질문을 입력하세요."
            , security = @SecurityRequirement(name = "accessToken")
    )
    public ApiResponse<QuestionResponseDTO.CreateResultDTO> createQuestion(
            @RequestBody @Valid QuestionRequestDTO.CreateDTO request
    ) {
        // accessToken으로 유효한 유저인지 인가
        tokenService.isValidToken(request.getUserId());

        return ApiResponse.onSuccess(
                SuccessStatus.Question_OK,
                QuestionConverter.toCreateResultDTO(
                        questionCommandService.createQuestion(request)
                )
        );
    }

    @PostMapping("/dummyQuestion")
    @Operation(
            summary = "질문 작성(더미 데이터) API"
            , description = "Header에 accessToken 필요. 로그인 된 Id로 총 100개의 질문이 생성됩니다. 어린이 여부는 'INACTICE'로 설정 됩니다."
            , security = @SecurityRequirement(name = "accessToken")
    )
    public void createDummyQuestion(Long userId) {
        // accessToken으로 유효한 유저인지 인가
        tokenService.isValidToken(userId);
        questionCommandService.createDummyQuestion(userId);
    }

    // 특정 질문 조회
    @GetMapping("/{questionId}")
    @Operation(summary = "특정 질문 조회 API",description = "# path variable로 조회 할 questionId를 입력하세요. \n" +
            "childStatus `ACTIVE`, `INACTIVE` 두가지로 나뉩니다. \n")
    public ApiResponse<QuestionResponseDTO.QuestionDTO> findQuestion(
            @PathVariable @ExistQuestion Long questionId
    ) {
        Question findQuestion = questionQueryService.findById(questionId);
        return ApiResponse.onSuccess(
                SuccessStatus.Question_OK,
                QuestionConverter.toQuestionDTO(
                        findQuestion,
                        questionQueryService.countExpertCountByQuestion(findQuestion)
                )
        );
    }

    // 전체 질문 페이징 조회 (검색 가능)
    @GetMapping
    @Operation(summary = "전체 질문 조회 API",description = "# RequestParam으로 페이징 조회를 위한 page와 size를 입력하세요. 검색을 원할 경우 search를 입력하세요. \n " +
            "childStatus는 `ACTIVE`, `INACTIVE` 두가지로 나뉩니다. \n")
    public ApiResponse<QuestionResponseDTO.QuestionPreviewListDTO> findQuestionByPaging(
            @RequestParam @Min(0) Integer page,
            @RequestParam @Min(1) @Max(10) Integer size,
            // Search
            @RequestParam(required = false) Optional<String> search

    ) {
        Page<Question> questions = questionQueryService.findAllBySearch(page, size, search);

        List<Integer> expertCounts = questionQueryService.findExpertCountByQuestion(questions);

        return ApiResponse.onSuccess(
                SuccessStatus.Question_OK,
                QuestionConverter.toQuestionPreviewDTOList(
                        questions,
                        expertCounts
                )
        );
    }

    // 질문 삭제
    @DeleteMapping("/{questionId}")
    @Operation(
            summary = "질문 삭제 API"
            , description = "path variable로 삭제할 questionId를 입력하세요."
            , security = @SecurityRequirement(name = "accessToken")
    )
    public ApiResponse<?> deleteQuestion(
            @ExistQuestion @PathVariable Long questionId,
            @RequestParam("userId") @ExistUser Long userId) {

        // accessToken으로 유효한 유저인지 인가
        tokenService.isValidToken(userId);

        questionCommandService.deleteQuestion(questionId);
        return ApiResponse.onSuccess(
                SuccessStatus.Question_OK,
                null
        );
    }


    // 질문 수정
    @PatchMapping("/{questionId}")
    @Operation(
            summary = "질문 수정 API"
            , description = "path variable로 questionId를 입력하고, Reauest Body에 수정할 title과 content를 입력하세요."
            , security = @SecurityRequirement(name = "accessToken")
    )
    public ApiResponse<QuestionResponseDTO.QuestionUpdateResultDTO> updateQuestion(
            @RequestBody @Valid QuestionRequestDTO.UpdateDTO request,
            @ExistQuestion @PathVariable Long questionId

    ) {
        // accessToken으로 유효한 유저인지 인가
        tokenService.isValidToken(request.getUserId());

        return ApiResponse.onSuccess(
                SuccessStatus.Question_OK,
                QuestionConverter.toQuestionUpdateReturnDTO(
                        questionCommandService.updateQuestion(questionId, request)
                )
        );
    }


    @GetMapping("/{questionId}/alarms")
    @Operation(
        summary = "특정 질문에 대한 답변 알림 조회 API"
        , description = "`path variable`로 알림에 대한 정보를 조회 하려는 `questionId`을 입력 하세요. \n." +
        " `response`로 해당 질문에 알림을 설정한 유저의 `userId`와 알람 설정 시간을 받습니다."
        , security = @SecurityRequirement(name = "accessToken")
    )
    public ApiResponse<UserQuestionAlarmListResultDTO> getQuestionAlarms(
        @Parameter(
            description = "알람 정보를 얻고 싶은 질문의 `questionId`를 `path variable`로 받습니다."
        )
        @PathVariable @ExistQuestion Long questionId
    ){
        List<UserQuestionAlarm> userQuestionAlarms = questionQueryService.getUserQuestionAlarms(questionId);
        return ApiResponse.onSuccess(
            SuccessStatus.Question_OK,
            QuestionConverter.toAlarmListResultDTO(questionId, userQuestionAlarms)
        );
    }
    @PostMapping("/{questionId}/alarms/user/{userId}")
    @Operation(
        summary = "특정 질문에 대한 답변 알림 설정 API"
        , description = "# Header에 accessToken 필요. \n"
        + "`path variable`로 알림을 설정 하려는 `questionId`와 `userId`을 입력 하세요. \n"
        + "유저가 특정 질문에 대해 알림을 설정 하는 API 입니다."
        , security = @SecurityRequirement(name = "accessToken")
    )
    public ApiResponse<UserQuestionAlarmDTO> setQuestionAlarms(
        @Parameter(
            description = "알람 정보를 설정할 `questionId`를 `path variable`로 받습니다."
        )
        @PathVariable @ExistQuestion Long questionId,
        @Parameter(
            description = "알람 정보를 설정할 `userId`를 `path variable`로 받습니다."
        )
        @PathVariable @ExistUser Long userId
    ){

        // accessToken으로 유효한 유저인지 인가
        tokenService.isValidToken(userId);

        UserQuestionAlarm userQuestionAlarm = questionCommandService.saveQuestionAlarm(questionId, userId);
        return ApiResponse.onSuccess(
            SuccessStatus.Question_OK,
            QuestionConverter.toUserQuestionAlarmDTO(userQuestionAlarm)
        );
    }


}
