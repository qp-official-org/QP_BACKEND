package qp.official.qp.apiPayload.exception.handler;

import qp.official.qp.apiPayload.code.BaseErrorCode;
import qp.official.qp.apiPayload.exception.GeneralException;

public class HashtagHandler extends GeneralException {
    public HashtagHandler(BaseErrorCode baseErrorCode){
        super(baseErrorCode);
    }

}
