package qp.official.qp.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import qp.official.qp.apiPayload.code.BaseErrorCode;
import qp.official.qp.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 회원 관려 에러 1000
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER_1001", "사용자가 없습니다."),
    NAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "USER_1002", "이름입력은 필수 입니다."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER_1003", "이미 존재하는 유저입니다."),

    // 질문 관려 에러 2000
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "QUESTION_2001", "찾고있는 질문글이 없습니다."),

    // 답변 관련 에러 3000
    ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND, "ANSWER_3001", "찾고있는 답변이 없습니다."),

    // 신고 관련 에러 4000
    ANSWERREPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "ANSWER_4001", "찾고있는 답변신고가 없습니다."),
    QUESTIONREPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "QUESTION_4002", "찾고있는 질문신고가 없습니다."),

    // 이미지 관련 에러 5000
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "IMAGE_5001", "찾고있는 이미지가 없습니다."),

    // 해시태그 관련 에러 6000
    HASHTAG_BAD_REQUEST(HttpStatus.BAD_REQUEST, "HASHTAG_6001", "해시태그 요청이 비어있습니다."),
    HASHTAG_NOT_FOUND(HttpStatus.NOT_FOUND, "HASHTAG_6001", "찾고있는 해시태그가 없습니다."),
    HASHTAG_ALREADY_EXISTS(HttpStatus.CONFLICT, "HASHTAG_6002", "이미 존재하는 해시태그입니다."),
    HASHTAG_NOT_EXIST(HttpStatus.BAD_REQUEST, "HASHTAG_6003", "해당 해시태그가 존재하지 않습니다.");

    // 답변 좋아요 관련 에러 7000

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
