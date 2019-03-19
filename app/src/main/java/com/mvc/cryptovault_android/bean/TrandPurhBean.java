package com.mvc.cryptovault_android.bean;

public class TrandPurhBean {

    /**
     * code : 0
     * data : {"balance":0,"max":0,"min":0,"price":0,"tokenBalance":0,"value":0}
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
         * max : 0
         * min : 0
         * price : 0
         * tokenBalance : 0
         * value : 0
         * minLimit
         */

        private double balance;
        private double max;
        private double min;
        private double price;
        private double tokenBalance;
        private double value;
        private double minLimit;

        public double getMinLimit() {
            return minLimit;
        }

        public void setMinLimit(double minLimit) {
            this.minLimit = minLimit;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getTokenBalance() {
            return tokenBalance;
        }

        public void setTokenBalance(double tokenBalance) {
            this.tokenBalance = tokenBalance;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}
