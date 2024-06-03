package com.mitrais.bootcamp.common.dto;

public abstract class BaseServiceResponse {
    protected long amount;
    protected String accountNumber;

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
