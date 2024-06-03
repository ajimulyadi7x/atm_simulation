package com.mitrais.bootcamp.view.impl;

import com.mitrais.bootcamp.common.auth.SecurityContext;
import com.mitrais.bootcamp.common.dto.ATMSimulationResult;
import com.mitrais.bootcamp.common.dto.BaseScreenResponseData;
import com.mitrais.bootcamp.common.dto.ScreenRequestData;
import com.mitrais.bootcamp.common.enums.ScreenCode;
import com.mitrais.bootcamp.common.util.ScannerUtil;
import com.mitrais.bootcamp.view.Screen;

public class TransactionScreenImpl implements Screen {

    @Override
    public ATMSimulationResult<BaseScreenResponseData> renderScreen(ScreenRequestData requestData) {

        ATMSimulationResult<BaseScreenResponseData> response = new ATMSimulationResult<>();
        System.out.println("---------------------------------");
        System.out.println("1 Withdraw             ");
        System.out.println("2 Fund Transfer        ");
        System.out.println("3 Exit                 ");
        System.out.println("Please choose option[3]:");
        System.out.println("---------------------------------");

        Integer input = ScannerUtil.getInstance().getInput(3);
        

        switch (input) {
        case 1:
            response.setNextScreen(ScreenCode.WITHDRAWAL_SCREEN);
            break;
        case 2:
            response.setNextScreen(ScreenCode.FUNDTRANSFER_SCREEN);
            break;
        case 3:
            SecurityContext.getInstance().setAuthenticatedAccount(null);
            response.setNextScreen(ScreenCode.WELCOME_SCREEN);
            break;
        default:
            response.setNextScreen(ScreenCode.TRANSACTION_SCREEN);
        }

        return response;
    }
}