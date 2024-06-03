package com.mitrais.bootcamp.view.impl;

import com.mitrais.bootcamp.common.auth.SecurityContext;
import com.mitrais.bootcamp.common.dto.*;
import com.mitrais.bootcamp.common.enums.ScreenCode;
import com.mitrais.bootcamp.minibank.service.Transaction;
import com.mitrais.bootcamp.common.util.AppUtils;
import com.mitrais.bootcamp.common.util.DateUtil;
import com.mitrais.bootcamp.common.util.ScannerUtil;
import com.mitrais.bootcamp.view.Screen;

import static com.mitrais.bootcamp.common.constant.AppConstant.CURRENCY;
import static com.mitrais.bootcamp.common.constant.TransactionConstant.*;

public class WithdrawalScreenImpl implements Screen {

    private final Transaction withdrawalService;

    public WithdrawalScreenImpl(Transaction withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @Override
    public ATMSimulationResult<BaseScreenResponseData> renderScreen(ScreenRequestData requestData) {
        ATMSimulationResult<BaseScreenResponseData> response = new ATMSimulationResult<>();

        boolean withdrawScreen = true;
        boolean continueTransaction = true;
        ResponseWrapper<BaseServiceResponse> responseWrapper = new ResponseWrapper<>();
        do {
            System.out.println("---------------------------------");
            System.out.printf("1. %s10 %n", CURRENCY);
            System.out.printf("2. %s50 %n", CURRENCY);
            System.out.printf("3. %s100 %n", CURRENCY);
            System.out.println("4. Other ");
            System.out.println("5. Back");
            System.out.println("Please choose option[5]:");
            System.out.println("---------------------------------");

            Integer input = ScannerUtil.getInstance().getInput(5);
            if (input == null) {
                continue;
            }

            boolean isSuccess = false;
            switch (input) {
            case 1:
            case 2:
            case 3:
                responseWrapper = doWithdraw(input, false);
                isSuccess = responseWrapper.isSuccess();
                break;
            case 4:
                responseWrapper = doOtherWithdraw();
                isSuccess = responseWrapper.isSuccess();
                break;
            case 5:
                withdrawScreen = false;
                response.setNextScreen(ScreenCode.TRANSACTION_SCREEN);
                break;
            }

            if (!isSuccess) {
                continue;
            }

            continueTransaction = confirmationPage(responseWrapper);
            if (continueTransaction) {
                continue;
            }
            response.setNextScreen(ScreenCode.TRANSACTION_SCREEN);
            withdrawScreen = false;
        } while (withdrawScreen);

        response.setSuccess(true);

        return response;
    }

    public ResponseWrapper<BaseServiceResponse> doWithdraw(Integer input, boolean isOtherWithdraw) {
        WithdrawalRequest transactionRequest = new WithdrawalRequest();
        transactionRequest.setAmount(isOtherWithdraw ? input : getAmountToWithdraw(input));
        ResponseWrapper<BaseServiceResponse> responseWrapper = withdrawalService.execute(transactionRequest);
        if (!responseWrapper.isSuccess()) {
            System.out.println(responseWrapper.getErrorDetail().getErrorMessage());
        }
        return responseWrapper;
    }

    public ResponseWrapper<BaseServiceResponse> doOtherWithdraw() {
        boolean exitOtherWithdraw = false;
        ResponseWrapper<BaseServiceResponse> responseWrapper = new ResponseWrapper<>();
        do {
            System.out.println("---------------------------------");
            System.out.println("Other Withdraw");
            System.out.println("Enter amount to withdraw and press enter to continue");
            System.out.println("or press enter to go back to Transaction: ");
            System.out.println("---------------------------------");
            String inputAmount = ScannerUtil.getInstance().getInputScanner().nextLine();
            if (inputAmount.isEmpty()) {
                continue;
            }

            Integer amount = validateAmount(inputAmount);
            if (amount != null) {
                responseWrapper = doWithdraw(amount, true);
                exitOtherWithdraw = true;
            }
        } while (!exitOtherWithdraw);

        return responseWrapper;
    }

    public boolean confirmationPage(ResponseWrapper<BaseServiceResponse> responseWrapper) {
        WithdrawalTrxSummaryDTO withdrawalTrxSummaryDTO = (WithdrawalTrxSummaryDTO) responseWrapper.getData();
        do {
            System.out.println("---------------------------------");
            System.out.println("Summary");
            System.out.printf("Date : %s%n", DateUtil.formatDate(withdrawalTrxSummaryDTO.getTrxDate()));
            System.out.printf("Withdraw : %s%s%n", CURRENCY, withdrawalTrxSummaryDTO.getAmount());
            System.out.printf("Balance : %s%s%n", CURRENCY, withdrawalTrxSummaryDTO.getCurrentBalance());
            System.out.println("1. Transaction");
            System.out.println("2. Exit");
            System.out.println("Choose option[2]:");
            System.out.println("---------------------------------");
            Integer optionInput = ScannerUtil.getInstance().getInput(2);
            if (optionInput == null) {
                continue;
            }
            switch (optionInput) {
            case 1:
                return true;
            case 2:
                SecurityContext.getInstance().setAuthenticatedAccount(null);
                return false;
            }
        } while (true);
    }

    private static Integer validateAmount(String inputAmount) {
        try {
            Integer amount = AppUtils.toNumeric(inputAmount);
            if (isMultiple(amount)) {
                System.out.printf("Invalid amount, the withdraw amount should be multiple of %s%s.%n",
                        CURRENCY, AMOUNT_MULTIPLY);
                return null;
            } else if (isMaxWithdraw(amount)) {
                System.out.printf("Maximum amount to withdraw is %s%s%n", CURRENCY, MAX_WITHDRAWAL_AMOUNT);
                return null;
            }
            return amount;
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount, should only contains numbers");
            return null;
        }
    }

    private long getAmountToWithdraw(Integer optionInput) {
        return WIDRAWAL_AMOUNT_OPTIONS.get(optionInput - 1);
    }

    private static boolean isMaxWithdraw(long amount) {
        return amount > MAX_WITHDRAWAL_AMOUNT;
    }

    private static boolean isMultiple(long amount) {
        return amount % AMOUNT_MULTIPLY != 0;
    }
}