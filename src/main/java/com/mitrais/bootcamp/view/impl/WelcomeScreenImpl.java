package com.mitrais.bootcamp.view.impl;

import com.mitrais.bootcamp.account.domain.Account;
import com.mitrais.bootcamp.account.service.AccountService;
import com.mitrais.bootcamp.common.constant.AppConstant;
import com.mitrais.bootcamp.common.dto.ATMSimulationResult;
import com.mitrais.bootcamp.common.dto.BaseScreenResponseData;
import com.mitrais.bootcamp.common.dto.ScreenRequestData;
import com.mitrais.bootcamp.common.enums.ScreenCode;
import com.mitrais.bootcamp.common.util.AppUtils;
import com.mitrais.bootcamp.minibank.domain.ErrorDetail;
import com.mitrais.bootcamp.common.util.ScannerUtil;
import com.mitrais.bootcamp.view.Screen;
import org.apache.commons.lang3.StringUtils;

import java.util.InputMismatchException;
import java.util.Scanner;

import static com.mitrais.bootcamp.common.constant.AppConstant.ACCOUNT_NUMBER_LENGTH;
import static com.mitrais.bootcamp.common.constant.AppConstant.PIN_LENGTH;
import static com.mitrais.bootcamp.common.enums.WelcomeScreenField.PIN;

public class WelcomeScreenImpl implements Screen {

    AccountService accountService;

    Scanner inputScanner = new Scanner(System.in);

    public WelcomeScreenImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public ATMSimulationResult<BaseScreenResponseData> renderScreen(ScreenRequestData requestData) {
        ATMSimulationResult<BaseScreenResponseData> response = new ATMSimulationResult<>();

        String accountNumber = "";
        String pin = "";
        do {
            if (accountNumber.isEmpty()) {
                System.out.println("---------------------------------");
                System.out.print("Enter Account Number : ");
                accountNumber = ScannerUtil.getInstance().getLoginInput("Account number", ACCOUNT_NUMBER_LENGTH);
                if (accountNumber.isEmpty())
                    continue;
            }

            if (pin.isEmpty()) {
                System.out.print("Enter PIN: ");
                pin = ScannerUtil.getInstance().getLoginInput("PIN", PIN_LENGTH);
            }
        } while (accountNumber.isEmpty() || pin.isEmpty());

        Account authenticatedAccount = accountService.login(accountNumber, pin);

        if(authenticatedAccount != null){
            response.setSuccess(true);
            response.setNextScreen(ScreenCode.TRANSACTION_SCREEN);
        }else{
            System.out.println("Invalid Account Number/PIN");
            response.setErrorContext(new ErrorDetail("01", ""));
        }

        return response;
    }

}