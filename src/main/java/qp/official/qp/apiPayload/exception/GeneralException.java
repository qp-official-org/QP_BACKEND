package qp.official.qp.apiPayload.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import qp.official.qp.apiPayload.code.BaseErrorCode;
import qp.official.qp.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}
