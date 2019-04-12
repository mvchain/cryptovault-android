package com.mvc.ttpay_android.bean;

import java.util.List;

public class TogeHisBean {

    /**
     * code : 200
     * data : [{"baseTokenName":"VRT","createdAt":1544618915950,"id":10,"price":66000,"projectId":1,"projectName":"测试项目1","projectOrderId":"P000000069","publishAt":0,"ratio":1000,"releaseValue":1,"reservationType":0,"stopAt":0,"successPayed":0,"successValue":0,"tokenId":5,"tokenName":"JYWD","value":66},{"baseTokenName":"VRT","createdAt":1544618512794,"id":9,"price":551000,"projectId":1,"projectName":"测试项目1","projectOrderId":"P000000068","publishAt":0,"ratio":1000,"releaseValue":1,"reservationType":0,"stopAt":0,"successPayed":0,"successValue":0,"tokenId":5,"tokenName":"JYWD","value":551},{"baseTokenName":"VRT","createdAt":1544609054648,"id":8,"price":1000,"projectId":1,"projectName":"测试项目1","projectOrderId":"P000000067","publishAt":0,"ratio":1000,"releaseValue":1,"reservationType":0,"stopAt":0,"successPayed":0,"successValue":0,"tokenId":5,"tokenName":"JYWD","value":1},{"baseTokenName":"VRT","createdAt":1544603122210,"id":7,"price":4000,"projectId":2,"projectName":"测试项目2","projectOrderId":"P000000066","publishAt":0,"ratio":1000,"releaseValue":1,"reservationType":0,"stopAt":0,"successPayed":0,"successValue":0,"tokenId":5,"tokenName":"JYWD","value":4},{"baseTokenName":"VRT","createdAt":1544603098862,"id":6,"price":1000,"projectId":2,"projectName":"测试项目2","projectOrderId":"P000000065","publishAt":0,"ratio":1000,"releaseValue":1,"reservationType":0,"stopAt":0,"successPayed":0,"successValue":0,"tokenId":5,"tokenName":"JYWD","value":1},{"baseTokenName":"VRT","createdAt":1544602937822,"id":5,"price":5000,"projectId":2,"projectName":"测试项目2","projectOrderId":"P000000064","publishAt":0,"ratio":1000,"releaseValue":1,"reservationType":0,"stopAt":0,"successPayed":0,"successValue":0,"tokenId":5,"tokenName":"JYWD","value":5},{"baseTokenName":"VRT","createdAt":1544602875248,"id":4,"price":5000,"projectId":2,"projectName":"测试项目2","projectOrderId":"P000000063","publishAt":0,"ratio":1000,"releaseValue":1,"reservationType":0,"stopAt":0,"successPayed":0,"successValue":0,"tokenId":5,"tokenName":"JYWD","value":5},{"baseTokenName":"VRT","createdAt":1544602808628,"id":3,"price":5000,"projectId":2,"projectName":"测试项目2","projectOrderId":"P000000062","publishAt":0,"ratio":1000,"releaseValue":1,"reservationType":0,"stopAt":0,"successPayed":0,"successValue":0,"tokenId":5,"tokenName":"JYWD","value":5},{"baseTokenName":"VRT","createdAt":0,"id":2,"price":10000,"projectId":2,"projectName":"测试项目2","projectOrderId":"TEXT2","publishAt":0,"ratio":1000,"releaseValue":1,"reservationType":0,"stopAt":0,"successPayed":1,"successValue":1,"tokenId":5,"tokenName":"JYWD","value":10},{"baseTokenName":"VRT","createdAt":0,"id":1,"price":10000,"projectId":1,"projectName":"测试项目1","projectOrderId":"TEXT1","publishAt":0,"ratio":1000,"releaseValue":1,"reservationType":0,"stopAt":0,"successPayed":1,"successValue":1,"tokenId":5,"tokenName":"JYWD","value":10}]
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
         * baseTokenName : VRT
         * createdAt : 1544618915950
         * id : 10
         * price : 66000
         * projectId : 1
         * projectName : 测试项目1
         * projectOrderId : P000000069
         * publishAt : 0
         * ratio : 1000
         * releaseValue : 1
         * reservationType : 0
         * stopAt : 0
         * successPayed : 0
         * successValue : 0
         * tokenId : 5
         * tokenName : JYWD
         * value : 66
         */

        private String baseTokenName;
        private long createdAt;
        private int id;
        private double price;
        private int projectId;
        private String projectName;
        private String projectOrderId;
        private long publishAt;
        private double ratio;
        private double releaseValue;
        private int reservationType;
        private long stopAt;
        private double successPayed;
        private double successValue;
        private int tokenId;
        private String tokenName;
        private double value;

        public String getBaseTokenName() {
            return baseTokenName;
        }

        public void setBaseTokenName(String baseTokenName) {
            this.baseTokenName = baseTokenName;
        }

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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getProjectId() {
            return projectId;
        }

        public void setProjectId(int projectId) {
            this.projectId = projectId;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getProjectOrderId() {
            return projectOrderId;
        }

        public void setProjectOrderId(String projectOrderId) {
            this.projectOrderId = projectOrderId;
        }

        public long getPublishAt() {
            return publishAt;
        }

        public void setPublishAt(long publishAt) {
            this.publishAt = publishAt;
        }

        public double getRatio() {
            return ratio;
        }

        public void setRatio(double ratio) {
            this.ratio = ratio;
        }

        public double getReleaseValue() {
            return releaseValue;
        }

        public void setReleaseValue(double releaseValue) {
            this.releaseValue = releaseValue;
        }

        public int getReservationType() {
            return reservationType;
        }

        public void setReservationType(int reservationType) {
            this.reservationType = reservationType;
        }

        public long getStopAt() {
            return stopAt;
        }

        public void setStopAt(long stopAt) {
            this.stopAt = stopAt;
        }

        public double getSuccessPayed() {
            return successPayed;
        }

        public void setSuccessPayed(double successPayed) {
            this.successPayed = successPayed;
        }

        public double getSuccessValue() {
            return successValue;
        }

        public void setSuccessValue(double successValue) {
            this.successValue = successValue;
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

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}
