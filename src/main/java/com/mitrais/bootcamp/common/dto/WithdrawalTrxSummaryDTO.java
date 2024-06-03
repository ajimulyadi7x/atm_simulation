package com.mitrais.bootcamp.common.dto;

import java.time.LocalDateTime;

public class WithdrawalTrxSummaryDTO extends BaseServiceResponse {
    private long          currentBalance;
    private LocalDateTime trxDate;

    public long getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(long currentBalance) {
        this.currentBalance = currentBalance;
    }

    public LocalDateTime getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(LocalDateTime trxDate) {
        this.trxDate = trxDate;
    }
}
