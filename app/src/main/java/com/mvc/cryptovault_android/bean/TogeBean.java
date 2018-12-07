package com.mvc.cryptovault_android.bean;

import java.util.List;

public class TogeBean {

    /**
     * code : 200
     * data : [{"baseTokenId":1,"baseTokenName":"VRT","createdAt":0,"projectId":4,"projectImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg","projectLimit":1000,"projectName":"测试项目4","ratio":1000,"releaseValue":1,"startedAt":0,"status":0,"stopAt":0,"tokenId":5,"tokenName":"JYWD","total":10000,"updatedAt":0},{"baseTokenId":1,"baseTokenName":"VRT","createdAt":0,"projectId":3,"projectImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg","projectLimit":1000,"projectName":"测试项目3","ratio":1000,"releaseValue":1,"startedAt":0,"status":0,"stopAt":0,"tokenId":5,"tokenName":"JYWD","total":10000,"updatedAt":0}]
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
         * baseTokenId : 1
         * baseTokenName : VRT
         * createdAt : 0
         * projectId : 4
         * projectImage : https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg
         * projectLimit : 1000
         * projectName : 测试项目4
         * ratio : 1000
         * releaseValue : 1
         * startedAt : 0
         * status : 0
         * stopAt : 0
         * tokenId : 5
         * tokenName : JYWD
         * total : 10000
         * updatedAt : 0
         */

        private int baseTokenId;
        private String baseTokenName;
        private int createdAt;
        private int projectId;
        private String projectImage;
        private int projectLimit;
        private String projectName;
        private int ratio;
        private int releaseValue;
        private int startedAt;
        private int status;
        private int stopAt;
        private int tokenId;
        private String tokenName;
        private int total;
        private int updatedAt;

        public int getBaseTokenId() {
            return baseTokenId;
        }

        public void setBaseTokenId(int baseTokenId) {
            this.baseTokenId = baseTokenId;
        }

        public String getBaseTokenName() {
            return baseTokenName;
        }

        public void setBaseTokenName(String baseTokenName) {
            this.baseTokenName = baseTokenName;
        }

        public int getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(int createdAt) {
            this.createdAt = createdAt;
        }

        public int getProjectId() {
            return projectId;
        }

        public void setProjectId(int projectId) {
            this.projectId = projectId;
        }

        public String getProjectImage() {
            return projectImage;
        }

        public void setProjectImage(String projectImage) {
            this.projectImage = projectImage;
        }

        public int getProjectLimit() {
            return projectLimit;
        }

        public void setProjectLimit(int projectLimit) {
            this.projectLimit = projectLimit;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public int getRatio() {
            return ratio;
        }

        public void setRatio(int ratio) {
            this.ratio = ratio;
        }

        public int getReleaseValue() {
            return releaseValue;
        }

        public void setReleaseValue(int releaseValue) {
            this.releaseValue = releaseValue;
        }

        public int getStartedAt() {
            return startedAt;
        }

        public void setStartedAt(int startedAt) {
            this.startedAt = startedAt;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStopAt() {
            return stopAt;
        }

        public void setStopAt(int stopAt) {
            this.stopAt = stopAt;
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

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(int updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
