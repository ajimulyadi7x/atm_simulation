package com.mitrais.bootcamp.common.dto;

public class FundTransferTrxSummaryDTO extends BaseServiceResponse {

    private String destinationAccountNumber;
    private long referenceNumber;
    private long sourceBalance;

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public void setDestinationAccountNumber(String destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public long getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(long referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public long getSourceBalance() {
        return sourceBalance;
    }

    public void setSourceBalance(long sourceBalance) {
        this.sourceBalance = sourceBalance;
    }
}
