package qp.official.qp.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qp.official.qp.apiPayload.ApiResponse;
import qp.official.qp.apiPayload.code.status.SuccessStatus;
import qp.official.qp.converter.QuestionConverter;
import qp.official.qp.web.dto.QuestionResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/questions")
public class QuestionRestController {

    @PostMapping("/test")
    public ApiResponse<QuestionResponseDTO.QuestionTestDTO> testAPI(){
        return ApiResponse.onSuccess(SuccessStatus.Question_OK.getCode(), SuccessStatus.Question_OK.getMessage(), QuestionConverter.toQuestionTestDTO());
    }
}
