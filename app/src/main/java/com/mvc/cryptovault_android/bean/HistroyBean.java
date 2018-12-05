package com.mvc.cryptovault_android.bean;

import java.util.List;

public class HistroyBean {

    /**
     * code : 200
     * data : [{"classify":0,"createdAt":0,"id":2,"ratio":1,"status":1,"tokenId":5,"tokenName":"JYWD","transactionType":1,"updatedAt":0,"value":2},{"classify":0,"createdAt":0,"id":1,"ratio":1,"status":1,"tokenId":5,"tokenName":"JYWD","transactionType":1,"updatedAt":0,"value":1}]
     * message :
     */

    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * classify : 0
         * createdAt : 0
         * id : 2
         * ratio : 1
         * status : 1
         * tokenId : 5
         * tokenName : JYWD
         * transactionType : 1
         * updatedAt : 0
         * value : 2
         */

        private int classify;
        private int createdAt;
        private int id;
        private int ratio;
        private int status;
        private int tokenId;
        private String tokenName;
        private int transactionType;
        private int updatedAt;
        private int value;

        public int getClassify() {
            return classify;
        }

        public void setClassify(int classify) {
            this.classify = classify;
        }

        public int getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(int createdAt) {
            this.createdAt = createdAt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRatio() {
            return ratio;
        }

        public void setRatio(int ratio) {
            this.ratio = ratio;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTokenId() {
            return tokenId;
        }

        public void setTokenId(int tokenId) {
            this.tokenId = tokenId;
        }

        public String getTokenName() {
            return tokenName;
        }

        public void setTokenName(String tokenName) {
            this.tokenName = tokenName;
        }

        public int getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(int transactionType) {
            this.transactionType = transactionType;
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