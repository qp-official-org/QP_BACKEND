package qp.official.qp.apiPayload.exception.handler;

import qp.official.qp.apiPayload.code.BaseErrorCode;
import qp.official.qp.apiPayload.exception.GeneralException;

public class AnswerHandler extends GeneralException {

    public AnswerHandler(BaseErrorCode baseErrorCode){
        super(baseErrorCode);
    }

}
