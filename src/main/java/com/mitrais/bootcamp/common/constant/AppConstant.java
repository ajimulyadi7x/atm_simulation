package com.mitrais.bootcamp.common.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppConstant {

    private AppConstant() {

    }

    public static final int           ACCOUNT_NUMBER_LENGTH = 6;
    public static final int           PIN_LENGTH            = 6;
    public static final String        CURRENCY        = "$";
    public static final List<Integer> AMOUNT_WITHDRAW = new ArrayList<>(Arrays.asList(10, 50, 100));
}
