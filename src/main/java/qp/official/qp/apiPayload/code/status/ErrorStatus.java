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
    USER_ID_NULL(HttpStatus.BAD_REQUEST, "USER_1004", "사용자 아이디는 필수 입니다."),
    USER_POINT_ZERO(HttpStatus.BAD_REQUEST, "USER_1005", "요청 포인트는 0이 아니어야 합니다."),
    USER_TOTALPOINT_NEGATIVE(HttpStatus.BAD_REQUEST, "USER_1006", "보유 포인트가 부족합니다."),


    // 질문 관려 에러 2000
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "QUESTION_2001", "찾고있는 질문글이 없습니다."),
    QUESTION_ID_NULL(HttpStatus.BAD_REQUEST, "USER_2002", "질문 아이디는 필수 입니다."),
    QUESTION_ALARM_NOT_FOUND(HttpStatus.NOT_FOUND, "QUESTION_2003", "해당 질문의 알람 정보가 존재 하지 않습니다."),
    QUESTION_ALARM_ALREADY_EXISTS(HttpStatus.CONFLICT, "QUESTION_2004", "해당 질문에 대한 알람 설정이 이미 존재 하는 유저 입니다."),
    QUESTION_ALARM_NOT_FOUND_BY_USER(HttpStatus.NOT_FOUND, "QUESTION_2005", "해당 유저가 설정한 알람은 존재 하지 않습니다."),
    QUESTION_NOT_EXIST_BY_USER(HttpStatus.NOT_FOUND, "QUESTION_2006", "해당 유저가 작성한 질문은 존재 하지 않습니다."),

    // 답변 관련 에러 3000
    ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND, "ANSWER_3001", "찾고있는 답변이 없습니다."),
    ANSWER_ID_NULL(HttpStatus.BAD_REQUEST, "USER_3002", "답변 아이디는 필수 입니다."),

    // 신고 관련 에러 4000
    ANSWERREPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "ANSWER_4001", "찾고있는 답변신고가 없습니다."),
    QUESTIONREPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "QUESTION_4002", "찾고있는 질문신고가 없습니다."),
    ANSWERREPORT_ID_NULL(HttpStatus.BAD_REQUEST, "USER_4003", "답변신고 아이디는 필수 입니다."),
    QUESTIONREPORT_ID_NULL(HttpStatus.BAD_REQUEST, "USER_4004", "질문신고 아이디는 필수 입니다."),

    // 이미지 관련 에러 5000
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "IMAGE_5001", "찾고 있는 이미지가 없습니다."),
    IMAGE_ALREADY_EXISTS(HttpStatus.CONFLICT, "IMAGE_5002", "이미 해당 파일 명이 존재합니다."),

    // 해시태그 관련 에러 6000
    HASHTAG_BAD_REQUEST(HttpStatus.BAD_REQUEST, "HASHTAG_6001", "해시태그 요청이 비어있습니다."),
    HASHTAG_NOT_FOUND(HttpStatus.NOT_FOUND, "HASHTAG_6002", "찾고있는 해시태그가 없습니다."),
    HASHTAG_ALREADY_EXISTS(HttpStatus.CONFLICT, "HASHTAG_6003", "이미 존재하는 해시태그입니다."),
    HASHTAG_NOT_EXIST(HttpStatus.BAD_REQUEST, "HASHTAG_6004", "해당 해시태그가 존재하지 않습니다."),
    HASHTAG_ID_NULL(HttpStatus.BAD_REQUEST, "USER_6005", "해시태그 아이디는 필수 입니다."),

    // 답변 좋아요 관련 에러 7000

    // 토큰 관련 에러 8000
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "TOKEN_8001","토큰이 만료되었습니다."),
    TOKEN_NOT_MATCH(HttpStatus.BAD_REQUEST, "TOKEN_8002", "로그인한 사용자와 토큰의 사용자가 일치하지 않습니다."),
    TOKEN_NOT_EXIST(HttpStatus.BAD_REQUEST, "TOKEN_8003", "토큰입력은 필수 입니다."),
    TOKEN_NOT_INCORRECT(HttpStatus.BAD_REQUEST, "TOKEN_8004", "입력 하신 토큰이 유효 하지 않습니다.");

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
