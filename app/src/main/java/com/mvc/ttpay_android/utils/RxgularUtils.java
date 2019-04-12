package com.mvc.ttpay_android.utils;

import java.util.regex.Pattern;

public class RxgularUtils {
    private static String ETH = "^(0x)?[0-9a-fA-F]{40}$";
    private static String BTC = "^[123mn][a-zA-Z1-9]{24,34}$";
    private static String EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

    public static boolean isETH(String content) {
        return Pattern.compile(ETH).matcher(content).matches();
    }

    public static boolean isBTC(String content) {
        return Pattern.compile(BTC).matcher(content).matches();
    }
    public static boolean isEmail(String content) {
        return Pattern.compile(EMAIL).matcher(content).matches();
    }
}
