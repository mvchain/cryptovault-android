package com.mvc.cryptovault_android.utils;

import java.util.regex.Pattern;

public class RxgularUtils {
    private static String eth = "^(0x)?[0-9a-fA-F]{40}$";
    private static String btc = "^[123mn][a-zA-Z1-9]{24,34}$";

    public static boolean isETH(String content) {
        return Pattern.compile(eth).matcher(content).matches();
    }

    public static boolean isBTC(String content) {
        return Pattern.compile(btc).matcher(content).matches();
    }
}
