package com.mitrais.bootcamp.common.auth;

import com.mitrais.bootcamp.account.domain.Account;

public class SecurityContext {

    private static SecurityContext INSTANCE;
    private Account authenticatedAccount;

    private SecurityContext() {

    }

    public static SecurityContext getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new SecurityContext();
        }
        return INSTANCE;
    }

    public Account getAuthenticatedAccount() {
        return authenticatedAccount;
    }

    public void setAuthenticatedAccount(Account authenticatedAccount) {
        this.authenticatedAccount = authenticatedAccount;
    }
}
