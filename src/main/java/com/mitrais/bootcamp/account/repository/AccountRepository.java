package com.mitrais.bootcamp.account.repository;

import com.mitrais.bootcamp.account.domain.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepository {

    private static List<Account> accounts = new ArrayList<Account>();
    static {
        Account account1 = new Account();
        account1.setName("John Doe");
        account1.setAccountNumber("112233");
        account1.setPin("012108");
        account1.setBalance(100);

        Account account2 = new Account();
        account2.setName("Jane Doe");
        account2.setAccountNumber("112244");
        account2.setPin("932012");
        account2.setBalance(30);

        accounts.add(account1);
        accounts.add(account2);
    }

    public List<Account> findAll(){
        return accounts;
    }

    public Optional<Account> findAccountByAccountNumber(String accountNumber) {
        return accounts.stream().filter(account -> account.getAccountNumber().equals(accountNumber)).findFirst();
    }

    public Optional<Account> findAccountByAccountNumberAndPin(String accountNumber, String pin) {
        return accounts.stream().filter(account -> account.getAccountNumber().equals(accountNumber) && account.getPin()
                .equalsIgnoreCase(pin)).findFirst();
    }

    public Account deductBalance(String accountNumber, long amount) {
        Optional<Account> account = findAccountByAccountNumber(accountNumber);
        if (account.isPresent()) {
            long currentBalance = account.get().getBalance();
            account.get().setBalance(currentBalance-amount);
            return account.get();
        }

        return null;
    }

    public Account increaseBalance(String accountNumber, long amount) {
        Optional<Account> account = findAccountByAccountNumber(accountNumber);
        if (account.isPresent()) {
            long currentBalance = account.get().getBalance();
            account.get().setBalance(currentBalance+amount);
            return account.get();
        }

        return null;
    }
}