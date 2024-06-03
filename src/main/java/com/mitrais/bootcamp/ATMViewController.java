package com.mitrais.bootcamp;

import com.mitrais.bootcamp.account.service.AccountService;
import com.mitrais.bootcamp.account.service.impl.AccountServiceImpl;
import com.mitrais.bootcamp.common.dto.ATMSimulationResult;
import com.mitrais.bootcamp.common.dto.BaseScreenResponseData;
import com.mitrais.bootcamp.account.repository.AccountRepository;
import com.mitrais.bootcamp.minibank.service.Transaction;
import com.mitrais.bootcamp.minibank.service.impl.FundTransferServiceImpl;
import com.mitrais.bootcamp.minibank.service.impl.WithdrawalServiceImpl;
import com.mitrais.bootcamp.view.Screen;
import com.mitrais.bootcamp.view.impl.*;

public class ATMViewController {

    private Screen welcomeScreen;
    private Screen transactionScreen;
    private Screen withdrawalScreen;
    private Screen fundTransferScreen;

    private static ATMViewController INSTANCE;

    private ATMViewController() {
        init();
    }

    public static ATMViewController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ATMViewController();
        }
        return INSTANCE;
    }


    private void init(){
        AccountRepository accountRepository = new AccountRepository();
        AccountService accountService = new AccountServiceImpl(accountRepository);

        welcomeScreen = new WelcomeScreenImpl(accountService);
        transactionScreen = new TransactionScreenImpl();

        Transaction withdrawalService = new WithdrawalServiceImpl(accountRepository);
        withdrawalScreen = new WithdrawalScreenImpl(withdrawalService);

        Transaction fundTransferService = new FundTransferServiceImpl(accountRepository);
        fundTransferScreen = new FundTransferScreenImpl(fundTransferService);
    }

    public void run() {
        ATMSimulationResult<BaseScreenResponseData> screen = welcomeScreen.renderScreen(null);
        do {
            switch (screen.getNextScreen()) {
            case TRANSACTION_SCREEN:
                screen.setNextScreen(transactionScreen.renderScreen(null).getNextScreen());
                break;
            case WITHDRAWAL_SCREEN:
                screen.setNextScreen(withdrawalScreen.renderScreen(null).getNextScreen());
                break;
            case FUNDTRANSFER_SCREEN:
                screen.setNextScreen(fundTransferScreen.renderScreen(null).getNextScreen());
                break;
            default:
                screen.setNextScreen(welcomeScreen.renderScreen(null).getNextScreen());
                break;
            }
        } while (screen.getNextScreen() != null);
    }


}