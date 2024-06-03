package com.mitrais.bootcamp.view.impl;

import com.mitrais.bootcamp.account.domain.Account;
import com.mitrais.bootcamp.common.auth.SecurityContext;
import com.mitrais.bootcamp.common.dto.*;
import com.mitrais.bootcamp.common.enums.ScreenCode;
import com.mitrais.bootcamp.common.util.AppUtils;
import com.mitrais.bootcamp.common.util.ScannerUtil;
import com.mitrais.bootcamp.minibank.service.Transaction;
import com.mitrais.bootcamp.view.Screen;

import static com.mitrais.bootcamp.common.constant.AppConstant.ACCOUNT_NUMBER_LENGTH;
import static com.mitrais.bootcamp.common.constant.AppConstant.CURRENCY;

public class FundTransferScreenImpl implements Screen {

    private Transaction fundTransferService;

    public FundTransferScreenImpl(Transaction fundTransferService) {
        this.fundTransferService = fundTransferService;
    }

    @Override
    public ATMSimulationResult<BaseScreenResponseData> renderScreen(ScreenRequestData requestData) {
        ATMSimulationResult<BaseScreenResponseData> response = new ATMSimulationResult<>();

        Account authenticatedAccount = SecurityContext.getInstance().getAuthenticatedAccount();

        boolean fundTransferScreen = true;
        ResponseWrapper<BaseServiceResponse> confirmationScreen = new ResponseWrapper<>();
        do {
            System.out.println("---------------------------------");
            System.out.println("Please enter destination account and press enter to continue");
            System.out.println("or press enter to go back to Transaction:");
            System.out.println("---------------------------------");

            String destinationAccount = ScannerUtil.getInstance().getInputScanner().nextLine();
            if (destinationAccount.isEmpty()) {
                fundTransferScreen = false;
                response.setNextScreen(ScreenCode.TRANSACTION_SCREEN);
                continue;
            }

            System.out.println("---------------------------------");
            System.out.println("Please enter transfer amount and press enter to continue");
            System.out.println("or press enter to go back to Transaction:");
            System.out.println("---------------------------------");

            String amountString = ScannerUtil.getInstance().getInputScanner().nextLine();
            if (amountString.isEmpty()) {
                fundTransferScreen = false;
                response.setNextScreen(ScreenCode.TRANSACTION_SCREEN);
                continue;
            }

            int refNumber = AppUtils.getRandomIntegerBetweenRange(900000, 100000);
            System.out.println("---------------------------------");
            System.out.printf("Reference Number: %s%n", refNumber);
            System.out.println("press enter key or any key to continue:");
            System.out.println("---------------------------------");

            ScannerUtil.getInstance().getInputScanner().nextLine();

            System.out.println("---------------------------------");
            System.out.println("Transfer Confirmation");
            System.out.printf("Destination Account : %s %n", destinationAccount);
            System.out.printf("Transfer Amount     : %s%s %n", CURRENCY, amountString);
            System.out.printf("Reference Number    : %s%n", refNumber);
            System.out.println("---------------------------------");

            confirmationScreen = confirmationScreen(authenticatedAccount, destinationAccount, amountString, refNumber);
            FundTransferTrxSummaryDTO fundTransferTrxSummaryDTO = (FundTransferTrxSummaryDTO) confirmationScreen.getData();

             if (confirmationScreen.isSuccess()) {
                summaryScreen(response, destinationAccount, amountString, refNumber, fundTransferTrxSummaryDTO);
                fundTransferScreen = false;
            } else {
                response.setNextScreen(ScreenCode.TRANSACTION_SCREEN);
            }
        } while (fundTransferScreen);

        return response;
    }

    private void summaryScreen(ATMSimulationResult<BaseScreenResponseData> response, String destinationAccount, String amountString, int refNumber, FundTransferTrxSummaryDTO fundTransferTrxSummaryDTO) {
        System.out.println("---------------------------------");
        System.out.println("Fund Transfer Summary");
        System.out.printf("Destination Account : %s %n", destinationAccount);
        System.out.printf("Transfer Amount     : %s%s %n", CURRENCY, amountString);
        System.out.printf("Reference Number    : %s%n", refNumber);
        System.out.printf("Balance : %s%s%n", CURRENCY, fundTransferTrxSummaryDTO.getSourceBalance());
        System.out.println();
        System.out.println("1. Transaction");
        System.out.println("2. Exit");
        System.out.println("Choose option[2]:");
        System.out.println("---------------------------------");
        Integer input = ScannerUtil.getInstance().getInput(1);

        switch (input) {
        case 1:
            response.setNextScreen(ScreenCode.TRANSACTION_SCREEN);
            break;
        case 2:
            response.setNextScreen(ScreenCode.WELCOME_SCREEN);
            break;
        }

    }

    private ResponseWrapper<BaseServiceResponse> confirmationScreen(Account authenticatedAccount, String destinationAccount, String amountString, int refNumber) {
        ResponseWrapper<BaseServiceResponse> responseWrapper = new ResponseWrapper<>();
        boolean confirmationScreen = true;
        do {
            System.out.println("---------------------------------");
            System.out.println("1. Confirm Trx");
            System.out.println("2. Cancel Trx");
            System.out.println("Choose option[2]:");
            System.out.println("---------------------------------");
            Integer input = ScannerUtil.getInstance().getInput(2);

            switch (input) {
            case 1:

                if (!AppUtils.isNumeric(destinationAccount) || destinationAccount.length() != ACCOUNT_NUMBER_LENGTH) {
                    System.out.printf("Invalid destination account." +
                                    " The account number should have %s digits and only contains numbers %n",
                            ACCOUNT_NUMBER_LENGTH);
                    confirmationScreen = false;
                    continue;
                } else if (destinationAccount.equals(authenticatedAccount.getAccountNumber())) {
                    System.out.println("Cannot transfer to the same account.");
                    confirmationScreen = false;
                    continue;
                }

                if (!AppUtils.isNumeric(amountString)) {
                    System.out.println("Invalid amount");
                    confirmationScreen = false;
                    continue;
                }

                Integer amount = AppUtils.toNumeric(amountString);

                FundTransferRequest fundTransferRequest = new FundTransferRequest();
                fundTransferRequest.setAmount(amount);
                fundTransferRequest.setDestAccountNumber(destinationAccount);

                responseWrapper = fundTransferService.execute(fundTransferRequest);
                if (!responseWrapper.isSuccess()) {
                    System.out.println(responseWrapper.getErrorDetail().getErrorMessage());
                }
                confirmationScreen = false;
                break;
            case 2:
                confirmationScreen = false;
                break;
            }
        } while (confirmationScreen);

        return responseWrapper;
    }
}