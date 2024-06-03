package com.mitrais.bootcamp.minibank.service.impl;

import com.mitrais.bootcamp.account.domain.Account;
import com.mitrais.bootcamp.account.repository.AccountRepository;
import com.mitrais.bootcamp.common.auth.SecurityContext;
import com.mitrais.bootcamp.common.dto.*;
import com.mitrais.bootcamp.common.enums.TransactionError;
import com.mitrais.bootcamp.minibank.domain.ErrorDetail;
import com.mitrais.bootcamp.minibank.exceptions.MiniBankException;
import com.mitrais.bootcamp.minibank.service.Transaction;
import com.mitrais.bootcamp.minibank.service.base.ServiceCallback;
import com.mitrais.bootcamp.minibank.service.base.ServiceTemplate;

import java.util.Optional;

public class FundTransferServiceImpl implements Transaction {

    AccountRepository accountRepository;

    public FundTransferServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public ResponseWrapper<BaseServiceResponse> execute(BaseTransactionRequest request) {
        ResponseWrapper<BaseServiceResponse> result = new ResponseWrapper<>();
        FundTransferRequest fundTransferRequest = (FundTransferRequest) request;

        ServiceTemplate.execute(result, new ServiceCallback() {
            @Override
            public void checkParameter() throws MiniBankException {
                if(fundTransferRequest.getAmount() == 0){
                    throw new MiniBankException(new ErrorDetail(TransactionError.INVALID_FT_MIN_AMOUNT.getCode(), TransactionError.INVALID_FT_MIN_AMOUNT.getMessage()));
                }

                if(fundTransferRequest.getAmount() > 1000){
                    throw new MiniBankException(new ErrorDetail(TransactionError.INVALID_FT_MAX_AMOUNT.getCode(), TransactionError.INVALID_FT_MAX_AMOUNT.getMessage()));
                }
            }

            @Override
            public void process() throws MiniBankException {

                Account authenticatedAccount = SecurityContext.getInstance().getAuthenticatedAccount();

                Optional<Account> destinationAccount = accountRepository.findAccountByAccountNumber(fundTransferRequest.getDestAccountNumber());

                if(!destinationAccount.isPresent()) {
                    throw new MiniBankException(new ErrorDetail(TransactionError.INVALID_ACCOUNT.getCode(), TransactionError.INVALID_ACCOUNT.getMessage()));
                }

                Account source = accountRepository.deductBalance(authenticatedAccount.getAccountNumber(), fundTransferRequest.getAmount());
                Account target = accountRepository.increaseBalance(destinationAccount.get().getAccountNumber(), fundTransferRequest.getAmount());

                FundTransferTrxSummaryDTO fundTransferTrxSummaryDTO = new FundTransferTrxSummaryDTO();
                fundTransferTrxSummaryDTO.setAccountNumber(source.getAccountNumber());
                fundTransferTrxSummaryDTO.setDestinationAccountNumber(String.valueOf(target.getAccountNumber()));
                fundTransferTrxSummaryDTO.setSourceBalance(source.getBalance());

                result.setSuccess(true);
                result.setData(fundTransferTrxSummaryDTO);

            }
        });

        return result;
    }
}