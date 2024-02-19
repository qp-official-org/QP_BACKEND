package qp.official.qp.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import qp.official.qp.apiPayload.code.BaseCode;
import qp.official.qp.apiPayload.code.ReasonDTO;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 유저 관련 응답
    User_OK(HttpStatus.OK, "USER_1000", "성공입니다."),

    // 질문 관련 응답
    Question_OK(HttpStatus.OK, "QUESTION_2000", "성공입니다."),

    // 답변 관련 응답
    Answer_OK(HttpStatus.OK, "ANSWER_3000", "성공입니다."),

    // 신고 관련 응답
    Report_OK(HttpStatus.OK, "REPORT_4000", "성공입니다."),

    // 이미지 관련 응답
    Image_OK(HttpStatus.OK, "IMAGE_5000", "성공입니다."),

    // 해시태그 관련 응답
    Hashtag_OK(HttpStatus.OK, "HASHTAG_6000", "성공입니다."),

    // 답변 좋아요 관련 응답
    AnswerLike_OK(HttpStatus.OK, "ANSWERLIKE_7000", "성공입니다."),

    // 전문가 관련 응답
    Expert_OK(HttpStatus.OK, "EXPERT_9000", "인증코드가 발급되었습니다."),
    EXPERT_AUTHORIZED(HttpStatus.OK, "EXPERT_9001", "전문가 인증이 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
