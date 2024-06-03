package com.mitrais.bootcamp.common.enums;
public enum ATMConstant {

    ACCOUNT_NUMBER_TYPE_REGEX ("\\d+"),
    ACCOUNT_NUMBER_LENGTH_REGEX ("\\d{6}"),
    PIN_TYPE_REGEX ("\\d+"),
    PIN_LENGTH_REGEX ("\\d{6}"),
    AMOUNT_TYPE_REGEX ("\\d+"),
    DESTINATION_ACCOUNT_FIELD_NAME ("destination account"),
    TRANSFER_AMOUNT_FIELD_NAME ("transfer amount")

    ;

    private String value;

    ATMConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}