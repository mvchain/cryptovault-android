package com.mvc.cryptovault_android.utils;

import java.text.DecimalFormat;

public class TextUtils {
    public static Integer stringToInt(String string) {
        return Integer.valueOf(string);
    }
    public static Double stringToDouble(String string) {
        return Double.parseDouble(string);
    }
    public static String doubleToFour(double price) {
        DecimalFormat format = new DecimalFormat("0.0000");
        return format.format(price);
    }
    public static float stringToFloat(String price) {
        return Float.valueOf(price);
    }
    public static String toFourFloat(float price) {
        DecimalFormat format = new DecimalFormat("0.0000");
        return format.format(price);
    }
}
