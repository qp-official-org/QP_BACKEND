package qp.official.qp.apiPayload.exception.handler;
import qp.official.qp.apiPayload.code.BaseErrorCode;
import qp.official.qp.apiPayload.exception.GeneralException;

public class TokenHandler extends GeneralException {
    public TokenHandler(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}

