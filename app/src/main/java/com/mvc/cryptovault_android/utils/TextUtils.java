package com.mvc.cryptovault_android.utils;

import com.blankj.utilcode.util.SPUtils;
import com.mvc.cryptovault_android.base.ExchangeRateBean;

import java.text.DecimalFormat;

import static com.mvc.cryptovault_android.common.Constant.SP.DEFAULE_RATE;
import static com.mvc.cryptovault_android.common.Constant.SP.SET_RATE;

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

    public static String doubleToDouble(double price) {
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(price);
    }

    //
    public static float stringToFloat(String price) {
        return Float.valueOf(price);
    }

    public static String toFourFloat(float price) {
        DecimalFormat format = new DecimalFormat("0.0000");
        return format.format(price);
    }

    public static String rateToPrice(double price) {
        DecimalFormat format = new DecimalFormat("0.00");
        String set_rate = SPUtils.getInstance().getString(SET_RATE);
        String default_rate = SPUtils.getInstance().getString(DEFAULE_RATE);
        ExchangeRateBean.DataBean setBean = (ExchangeRateBean.DataBean) JsonHelper.stringToJson(set_rate, ExchangeRateBean.DataBean.class);
        ExchangeRateBean.DataBean defaultBean = (ExchangeRateBean.DataBean) JsonHelper.stringToJson(default_rate, ExchangeRateBean.DataBean.class);
        if (setBean.getName().equals("CNY")) {
            return format.format(price * setBean.getValue());
        } else {
            return format.format(price / setBean.getValue());
        }
    }
}
