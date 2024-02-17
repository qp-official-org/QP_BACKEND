package qp.official.qp.apiPayload.exception.handler;

import qp.official.qp.apiPayload.code.BaseErrorCode;
import qp.official.qp.apiPayload.exception.GeneralException;

public class QuestionHandler extends GeneralException {

    public QuestionHandler(BaseErrorCode baseErrorCode){
        super(baseErrorCode);
    }

}
