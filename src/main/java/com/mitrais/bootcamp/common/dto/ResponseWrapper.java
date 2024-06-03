package com.mitrais.bootcamp.common.dto;

import com.mitrais.bootcamp.minibank.domain.ErrorDetail;

public class ResponseWrapper<T extends BaseServiceResponse> {
    private boolean success;
    private T data;

    private ErrorDetail errorDetail;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ErrorDetail getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(ErrorDetail errorDetail) {
        this.errorDetail = errorDetail;
    }
}
