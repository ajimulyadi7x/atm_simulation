package com.mitrais.bootcamp.common.dto;

import com.mitrais.bootcamp.common.enums.ScreenCode;
import com.mitrais.bootcamp.minibank.domain.ErrorDetail;

public abstract class BaseResult {

    protected boolean success;

    protected ScreenCode  nextScreen;
    protected ErrorDetail errorDetail;

    public BaseResult() {
        this.success = false;
        this.errorDetail = new ErrorDetail();
    }

    public BaseResult(boolean success, ErrorDetail errorDetail) {
        this.success = success;
        this.errorDetail = errorDetail;
    }

    public ScreenCode getNextScreen() {
        return nextScreen;
    }

    public void setNextScreen(ScreenCode nextScreen) {
        this.nextScreen = nextScreen;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ErrorDetail getErrorContext() {
        return errorDetail;
    }

    public void setErrorContext(ErrorDetail errorDetail) {
        this.errorDetail = errorDetail;
    }
}