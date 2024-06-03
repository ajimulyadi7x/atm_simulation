package com.mitrais.bootcamp.common.dto;

public class WithdrawalRequest implements BaseTransactionRequest {
    private String accountNumber;
    private long amount;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}