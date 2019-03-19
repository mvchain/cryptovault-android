package com.mvc.cryptovault_android.bean;

import java.util.List;

public class TrandOrderBean {

    /**
     * code : 0
     * data : [{"createdAt":0,"deal":0,"id":0,"nickname":"string","orderNumber":"string","pairId":0,"price":0,"transactionType":0,"updatedAt":0}]
     * message : string
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
         * createdAt : 0
         * deal : 0
         * id : 0
         * nickname : string
         * orderNumber : string
         * pairId : 0
         * price : 0
         * transactionType : 0
         * updatedAt : 0
         */

        private long createdAt;
        private double deal;
        private int id;
        private String nickname;
        private String orderNumber;
        private int pairId;
        private double price;
        private int status;
        private int transactionType;
        private long updatedAt;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(long createdAt) {
            this.createdAt = createdAt;
        }

        public double getDeal() {
            return deal;
        }

        public void setDeal(double deal) {
            this.deal = deal;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public int getPairId() {
            return pairId;
        }

        public void setPairId(int pairId) {
            this.pairId = pairId;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(int transactionType) {
            this.transactionType = transactionType;
        }

        public long getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(long updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
