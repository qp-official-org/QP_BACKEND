package qp.official.qp.apiPayload.exception.handler;

import qp.official.qp.apiPayload.code.BaseErrorCode;
import qp.official.qp.apiPayload.exception.GeneralException;

public class ImageHandler extends GeneralException {


    public ImageHandler(BaseErrorCode code) {
        super(code);
    }
}
