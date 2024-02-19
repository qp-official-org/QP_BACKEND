package qp.official.qp.apiPayload.exception.handler;

import qp.official.qp.apiPayload.code.BaseErrorCode;
import qp.official.qp.apiPayload.exception.GeneralException;

public class ExpertHandler extends GeneralException {
    public ExpertHandler(BaseErrorCode baseErrorCode){
        super(baseErrorCode);
    }
}
