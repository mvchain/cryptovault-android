package com.mvc.cryptovault_android.bean;

public class PurchaseBean {

    /**
     * code : 0
     * data : {"balance":0,"limitValue":0,"projectMin":0}
     * message : string
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * balance : 0
         * limitValue : 0
         * projectMin : 0
         */

        private double balance;
        private double limitValue;
        private double projectMin;

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public double getLimitValue() {
            return limitValue;
        }

        public void setLimitValue(double limitValue) {
            this.limitValue = limitValue;
        }

        public double getProjectMin() {
            return projectMin;
        }

        public void setProjectMin(double projectMin) {
            this.projectMin = projectMin;
        }
    }
}
