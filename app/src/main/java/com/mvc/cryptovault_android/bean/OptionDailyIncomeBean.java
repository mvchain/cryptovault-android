package com.mvc.cryptovault_android.bean;

import java.util.ArrayList;
import java.util.List;

public class OptionDailyIncomeBean {

    /**
     * code : 0
     * data : [{"createdAt":0,"id":0,"income":0,"tokenName":"string"}]
     * message : string
     */

    private int code;
    private String message;
    private ArrayList<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createdAt : 0
         * id : 0
         * income : 0
         * tokenName : string
         */

        private long createdAt;
        private int id;
        private double income;
        private String tokenName;

        public long getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(long createdAt) {
            this.createdAt = createdAt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getIncome() {
            return income;
        }

        public void setIncome(double income) {
            this.income = income;
        }

        public String getTokenName() {
            return tokenName;
        }

        public void setTokenName(String tokenName) {
            this.tokenName = tokenName;
        }
    }
}
