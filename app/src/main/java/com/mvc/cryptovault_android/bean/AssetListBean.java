package com.mvc.cryptovault_android.bean;

import java.util.List;

public class AssetListBean {

    /**
     * code : 200
     * data : [{"ratio":0.1492537313432836,"tokenId":1,"tokenName":"VRT","value":1000997.1799403675},{"ratio":0.1492537313432836,"tokenId":2,"tokenName":"余额","value":999985},{"ratio":1,"tokenId":3,"tokenName":"ETH","value":1000009.7574906155},{"ratio":0.001,"tokenId":4,"tokenName":"USDT","value":1000000},{"ratio":0,"tokenId":10,"tokenName":"MVC0.5131705911","value":0},{"ratio":0,"tokenId":17,"tokenName":"NJH","value":3.85E-10}]
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
         * ratio : 0.1492537313432836
         * tokenId : 1
         * tokenName : VRT
         * value : 1000997.1799403675
         */

        private double ratio;
        private int tokenId;
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
