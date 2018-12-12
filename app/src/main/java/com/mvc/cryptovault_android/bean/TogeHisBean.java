package com.mvc.cryptovault_android.bean;

import java.util.List;

public class TogeHisBean {

    /**
     * code : 200
     * data : [{"createdAt":0,"id":2,"price":10000,"projectId":2,"projectName":"测试项目2","projectOrderId":"TEXT2","publishAt":0,"ratio":1000,"releaseValue":1,"reservationType":0,"stopAt":0,"value":10},{"createdAt":0,"id":1,"price":10000,"projectId":1,"projectName":"测试项目1","projectOrderId":"TEXT1","publishAt":0,"ratio":1000,"releaseValue":1,"reservationType":0,"stopAt":0,"value":10}]
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
         * createdAt : 0
         * id : 2
         * price : 10000
         * projectId : 2
         * projectName : 测试项目2
         * projectOrderId : TEXT2
         * publishAt : 0
         * ratio : 1000
         * releaseValue : 1
         * reservationType : 0
         * stopAt : 0
         * value : 10
         */

        private long createdAt;
        private int id;
        private double price;
        private int projectId;
        private String projectName;
        private String projectOrderId;
        private int publishAt;
        private double ratio;
        private double releaseValue;
        private int reservationType;
        private long stopAt;
        private double value;

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

        public int getPublishAt() {
            return publishAt;
        }

        public void setPublishAt(int publishAt) {
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

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}
