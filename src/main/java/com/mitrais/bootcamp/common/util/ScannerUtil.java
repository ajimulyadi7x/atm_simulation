package com.mitrais.bootcamp.common.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerUtil {

    private static ScannerUtil INSTANCE;
    private static Scanner inputScanner = new Scanner(System.in);

    private ScannerUtil() {

    }
    public static ScannerUtil getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ScannerUtil();
        }
        return INSTANCE;
    }


    public String getLoginInput(String field, Integer maxInput) {
        String input = "";
        try {
            input = inputScanner.nextLine();
            if (maxInput != null && input.length() != maxInput) {
                System.out.printf(" %s should have %s digits %n", field, maxInput);
                return "";
            }
            if (!AppUtils.isNumeric(input)) {
                System.out.printf(" %s should only contains numbers %n", field);
                return "";
            }
            return input;
        } catch (InputMismatchException e) {
            return "";
        }
    }

    public Integer getInput(Integer defaultOption) {
        try {
            String input = inputScanner.nextLine();
            if (defaultOption != null && input.isEmpty()) {
                return defaultOption;
            } else if (defaultOption == null && input.isEmpty()) {
                return null;
            }

            if (AppUtils.isNumeric(input)) {
                return AppUtils.toNumeric(input);
            } else {
                return null;
            }
        } catch (InputMismatchException e) {
            return null;
        }
    }

    public Scanner getInputScanner() {
        return inputScanner;
    }

    public static void setInputScanner(Scanner inputScanner) {
        ScannerUtil.inputScanner = inputScanner;
    }
}
