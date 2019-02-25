package com.mvc.cryptovault_android.bean;

import com.google.gson.annotations.SerializedName;

public class AssetsBean {
    /**
     * code : 0
     * data : {"ratio":0,"tokenId":0,"tokenImage":"string","tokenName":"string","value":0}
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
         * ratio : 0
         * tokenId : 0
         * tokenImage : string
         * tokenName : string
         * value : 0
         */

        private double ratio;
        private int tokenId;
        private String tokenImage;
        private String tokenName;
        private double value;

        public double getRatio() {
            return ratio;
        }

        public void setRatio(double ratio) {
            this.ratio = ratio;
        }

        public int getTokenId() {
            return tokenId;
        }

        public void setTokenId(int tokenId) {
            this.tokenId = tokenId;
        }

        public String getTokenImage() {
            return tokenImage;
        }

        public void setTokenImage(String tokenImage) {
            this.tokenImage = tokenImage;
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
