package com.mitrais.bootcamp.common.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionConstant {

    private TransactionConstant() {}

    public static final List<Integer> WIDRAWAL_AMOUNT_OPTIONS = new ArrayList<>(Arrays.asList(10, 50, 100));
    public static final long MAX_WITHDRAWAL_AMOUNT = 1000;
    public static final long AMOUNT_MULTIPLY = 10;

}
