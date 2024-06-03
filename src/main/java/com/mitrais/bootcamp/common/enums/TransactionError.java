package com.mitrais.bootcamp.common.enums;

public enum TransactionError {
    INVALID_AMOUNT("INVALID_AMOUNT", "Invalid amount")
    , INVALID_ACCOUNT("INVALID_ACCOUNT", "Invalid account")
    , INSUFFICIENT_BALANCE("INSUFFICIENT_BALANCE", "Insufficient balance")
    , INVALID_FT_MIN_AMOUNT("INVALID_FT_MIN_AMOUNT", "Minimum amount to transfer is $1")
    , INVALID_FT_MAX_AMOUNT("INVALID_FT_MAX_AMOUNT", "Maximum amount to transfer is $1000");

    private String code;
    private String message;

    TransactionError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
