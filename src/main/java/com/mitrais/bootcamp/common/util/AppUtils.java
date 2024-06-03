package com.mitrais.bootcamp.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class AppUtils {

    private static final String NUMERIC_REGEX = "^[0-9]*$";

    private static final Random random = new Random();

    private AppUtils() {
    }

    public static boolean isNumeric(String numString) {
        return Pattern.matches(NUMERIC_REGEX, numString);
    }

    public static Integer toNumeric(String numString) {
        return Integer.parseInt(numString);
    }

    public static int getRandomIntegerBetweenRange(int min, int max) {
        return (random.nextInt() * ((max - min) + 1)) + min;
    }

}
