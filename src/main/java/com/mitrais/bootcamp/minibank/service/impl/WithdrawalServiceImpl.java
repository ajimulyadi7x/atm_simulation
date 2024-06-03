package com.mitrais.bootcamp.minibank.service.impl;

import com.mitrais.bootcamp.account.domain.Account;
import com.mitrais.bootcamp.common.auth.SecurityContext;
import com.mitrais.bootcamp.common.constant.AppConstant;
import com.mitrais.bootcamp.common.dto.*;
import com.mitrais.bootcamp.common.enums.TransactionError;
import com.mitrais.bootcamp.minibank.domain.ErrorDetail;
import com.mitrais.bootcamp.account.repository.AccountRepository;
import com.mitrais.bootcamp.minibank.exceptions.MiniBankException;
import com.mitrais.bootcamp.minibank.repository.TransactionRepository;
import com.mitrais.bootcamp.minibank.service.base.ServiceCallback;
import com.mitrais.bootcamp.minibank.service.base.ServiceTemplate;
import com.mitrais.bootcamp.minibank.service.Transaction;

import java.time.LocalDateTime;
import java.util.Optional;

public class WithdrawalServiceImpl implements Transaction {

    AccountRepository     accountRepository;
    TransactionRepository transactionRepository;

    public WithdrawalServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public ResponseWrapper<BaseServiceResponse> execute(BaseTransactionRequest request) {
        ResponseWrapper<BaseServiceResponse> result = new ResponseWrapper<>();

        WithdrawalRequest withdrawalRequest = (WithdrawalRequest) request;

        ServiceTemplate.execute(result, new ServiceCallback() {
            @Override
            public void checkParameter() throws MiniBankException {


                if(withdrawalRequest.getAmount() == 0 || withdrawalRequest.getAmount() % 10 != 0){
                    throw new MiniBankException(new ErrorDetail(TransactionError.INVALID_AMOUNT.getCode(), TransactionError.INVALID_AMOUNT.getMessage()));
                }

                if(withdrawalRequest.getAmount() > 1000){
                    throw new MiniBankException(new ErrorDetail(TransactionError.INVALID_AMOUNT.getCode(), TransactionError.INVALID_AMOUNT.getMessage()));
                }

            }

            @Override
            public void process() throws MiniBankException {

                Account authenticatedAccount = SecurityContext.getInstance().getAuthenticatedAccount();

                Optional<Account> account = accountRepository.findAccountByAccountNumber(
                        authenticatedAccount.getAccountNumber());

                if(!account.isPresent()) {
                    throw new MiniBankException(new ErrorDetail(TransactionError.INVALID_ACCOUNT.getCode(), TransactionError.INVALID_ACCOUNT.getMessage()));
                }

                long currentBalance = account.get().getBalance();

                if(currentBalance < withdrawalRequest.getAmount()){
                    StringBuilder errorMsgBuilder = new StringBuilder();
                    errorMsgBuilder.append(TransactionError.INSUFFICIENT_BALANCE.getMessage()).append(" ").append(AppConstant.CURRENCY).append(currentBalance);
                    throw new MiniBankException(new ErrorDetail(TransactionError.INSUFFICIENT_BALANCE.getCode(), errorMsgBuilder.toString()));
                }

                Account updatedAccount = accountRepository.deductBalance(authenticatedAccount.getAccountNumber(),
                        withdrawalRequest.getAmount());

                WithdrawalTrxSummaryDTO withdrawalResponse = new WithdrawalTrxSummaryDTO();
                withdrawalResponse.setCurrentBalance(updatedAccount.getBalance());
                withdrawalResponse.setTrxDate(LocalDateTime.now());
                withdrawalResponse.setAmount(withdrawalRequest.getAmount());

                result.setSuccess(true);
                result.setData(withdrawalResponse);

            }
        });

        return result;
    }
}