package com.mvc.cryptovault_android.bean;

public class DetailBean {

    /**
     * code : 0
     * data : {"classify":0,"createdAt":0,"fee":0,"feeTokenType":"string","fromAddress":"string","hash":"string","hashLink":"string","status":0,"toAddress":"string","tokenName":"string","updatedAt":0,"value":0}
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
         * classify : 0
         * createdAt : 0
         * fee : 0
         * feeTokenType : string
         * fromAddress : string
         * hash : string
         * hashLink : string
         * status : 0
         * toAddress : string
         * tokenName : string
         * updatedAt : 0
         * value : 0
         */

        private int classify;
        private long createdAt;
        private double fee;
        private String feeTokenType;
        private String fromAddress;
        private String hash;
        private String hashLink;
        private int status;
        private String toAddress;
        private String tokenName;
        private int updatedAt;
        private int value;

        public int getClassify() {
            return classify;
        }

        public void setClassify(int classify) {
            this.classify = classify;
        }

        public long getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(long createdAt) {
            this.createdAt = createdAt;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }

        public String getFeeTokenType() {
            return feeTokenType;
        }

        public void setFeeTokenType(String feeTokenType) {
            this.feeTokenType = feeTokenType;
        }

        public String getFromAddress() {
            return fromAddress;
        }

        public void setFromAddress(String fromAddress) {
            this.fromAddress = fromAddress;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getHashLink() {
            return hashLink;
        }

        public void setHashLink(String hashLink) {
            this.hashLink = hashLink;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getToAddress() {
            return toAddress;
        }

        public void setToAddress(String toAddress) {
            this.toAddress = toAddress;
        }

        public String getTokenName() {
            return tokenName;
        }

        public void setTokenName(String tokenName) {
            this.tokenName = tokenName;
        }

        public int getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(int updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
