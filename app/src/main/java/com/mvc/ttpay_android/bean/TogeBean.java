package com.mvc.ttpay_android.bean;

import android.os.Parcel;
import android.os.Parcelable;

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

    public static class DataBean implements Parcelable {
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
        private long createdAt;
        private int projectId;
        private String projectImage;
        private double projectLimit;
        private String projectName;
        private double ratio;
        private double releaseValue;
        private long startedAt;
        private int status;
        private long stopAt;
        private int tokenId;
        private String tokenName;
        private double total;
        private long updatedAt;

        protected DataBean(Parcel in) {
            baseTokenId = in.readInt();
            baseTokenName = in.readString();
            createdAt = in.readLong();
            projectId = in.readInt();
            projectImage = in.readString();
            projectLimit = in.readDouble();
            projectName = in.readString();
            ratio = in.readDouble();
            releaseValue = in.readDouble();
            startedAt = in.readLong();
            status = in.readInt();
            stopAt = in.readLong();
            tokenId = in.readInt();
            tokenName = in.readString();
            total = in.readDouble();
            updatedAt = in.readLong();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

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

        public long getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(long createdAt) {
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

        public double getProjectLimit() {
            return projectLimit;
        }

        public void setProjectLimit(double projectLimit) {
            this.projectLimit = projectLimit;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
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

        public long getStartedAt() {
            return startedAt;
        }

        public void setStartedAt(long startedAt) {
            this.startedAt = startedAt;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getStopAt() {
            return stopAt;
        }

        public void setStopAt(long stopAt) {
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

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public long getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(long updatedAt) {
            this.updatedAt = updatedAt;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(baseTokenId);
            dest.writeString(baseTokenName);
            dest.writeLong(createdAt);
            dest.writeInt(projectId);
            dest.writeString(projectImage);
            dest.writeDouble(projectLimit);
            dest.writeString(projectName);
            dest.writeDouble(ratio);
            dest.writeDouble(releaseValue);
            dest.writeLong(startedAt);
            dest.writeInt(status);
            dest.writeLong(stopAt);
            dest.writeInt(tokenId);
            dest.writeString(tokenName);
            dest.writeDouble(total);
            dest.writeLong(updatedAt);
        }
    }
}