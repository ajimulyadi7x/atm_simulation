package com.mitrais.bootcamp.minibank.exceptions;

import com.mitrais.bootcamp.minibank.domain.ErrorDetail;

public class MiniBankException extends Exception {
    private ErrorDetail errorDetail;
    public MiniBankException(ErrorDetail errorDetail) {
        this.errorDetail = errorDetail;
    }

    public ErrorDetail getErrorDetail() {
        return errorDetail;
    }
}
