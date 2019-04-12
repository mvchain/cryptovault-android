package com.mvc.ttpay_android.bean;

import java.util.List;

public class PublishBean {

    /**
     * code : 0
     * data : [{"projectId":0,"projectName":"string","publishTime":0,"releaseValue":0}]
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
         * projectId : 0
         * projectName : string
         * publishTime : 0
         * releaseValue : 0
         */

        private int projectId;
        private String projectName;
        private String projectImage;
        private long publishTime;
        private double releaseValue;

        public String getProjectImage() {
            return projectImage;
        }

        public void setProjectImage(String projectImage) {
            this.projectImage = projectImage;
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

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public double getReleaseValue() {
            return releaseValue;
        }

        public void setReleaseValue(double releaseValue) {
            this.releaseValue = releaseValue;
        }
    }
}
