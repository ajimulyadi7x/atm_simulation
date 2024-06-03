/**
 * Alipay.com Inc.
 * Copyright (c) 2004‐2020 All Rights Reserved.
 */
package com.mitrais.bootcamp.account.service;

import com.mitrais.bootcamp.account.domain.Account;
public interface AccountService {

    boolean validateUser(Account condition);

    Account login(String accountNumber, String pin);
}