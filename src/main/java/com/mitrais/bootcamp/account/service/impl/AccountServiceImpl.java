/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.account.service.impl;

import com.mitrais.bootcamp.account.domain.Account;
import com.mitrais.bootcamp.account.repository.AccountRepository;
import com.mitrais.bootcamp.account.service.AccountService;
import com.mitrais.bootcamp.common.auth.SecurityContext;

import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean validateUser(Account condition) {
        List<Account> dataList = accountRepository.findAll();
        return dataList.size() > 0 ? true : false;
    }

    @Override
    public Account login(String accountNumber, String pin) {
        Optional<Account> account = accountRepository.findAccountByAccountNumberAndPin(accountNumber, pin);
        if (account.isPresent()) {
            SecurityContext.getInstance().setAuthenticatedAccount(account.get());
            return account.get();
        }

        return null;
    }

}