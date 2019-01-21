package com.mvc.cryptovault_android.bean;

public class OptionDetailBean {

    /**
     * code : 200
     * data : {"baseTokenName":"USDT","financialName":"cctv","income":0,"incomeMax":5,"incomeMin":1,"times":11,"tokenName":"VRT","value":2}
     * message :
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
         * baseTokenName : USDT
         * financialName : cctv
         * income : 0
         * incomeMax : 5
         * incomeMin : 1
         * times : 11
         * tokenName : VRT
         * value : 2
         */

        private String baseTokenName;
        private String financialName;
        private double income;
        private int incomeMax;
        private int incomeMin;
        private int times;
        private String tokenName;
        private double value;

        public String getBaseTokenName() {
            return baseTokenName;
        }

        public void setBaseTokenName(String baseTokenName) {
            this.baseTokenName = baseTokenName;
        }

        public String getFinancialName() {
            return financialName;
        }

        public void setFinancialName(String financialName) {
            this.financialName = financialName;
        }

        public double getIncome() {
            return income;
        }

        public void setIncome(double income) {
            this.income = income;
        }

        public int getIncomeMax() {
            return incomeMax;
        }

        public void setIncomeMax(int incomeMax) {
            this.incomeMax = incomeMax;
        }

        public int getIncomeMin() {
            return incomeMin;
        }

        public void setIncomeMin(int incomeMin) {
            this.incomeMin = incomeMin;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        public String getTokenName() {
            return tokenName;
        }

        public void setTokenName(String tokenName) {
            this.tokenName = tokenName;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}
