package com.mvc.ttpay_android.bean;

public class IDToTransferBean {

    /**
     * code : 0
     * data : {"balance":0,"fee":0,"feeTokenName":"string"}
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
         * fee : 0
         * feeTokenName : string
         */

        private double balance;
        private float fee;
        private String feeTokenName;

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public float getFee() {
            return fee;
        }

        public void setFee(float fee) {
            this.fee = fee;
        }

        public String getFeeTokenName() {
            return feeTokenName;
        }

        public void setFeeTokenName(String feeTokenName) {
            this.feeTokenName = feeTokenName;
        }
    }
}
