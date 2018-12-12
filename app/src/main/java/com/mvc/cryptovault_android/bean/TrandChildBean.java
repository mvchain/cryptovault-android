package com.mvc.cryptovault_android.bean;

import java.util.List;

public class TrandChildBean {

    /**
     * code : 200
     * data : [{"increase":650,"pair":"VRT/USDT","pairId":1,"ratio":6.5,"tokenId":4,"tokenImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg","tokenName":"USDT","transactionStatus":0},{"increase":100,"pair":"VRT/JYWD","pairId":2,"ratio":1,"tokenId":5,"tokenImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg","tokenName":"JYWD","transactionStatus":1}]
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
         * increase : 650
         * pair : VRT/USDT
         * pairId : 1
         * ratio : 6.5
         * tokenId : 4
         * tokenImage : https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg
         * tokenName : USDT
         * transactionStatus : 0
         */

        private double increase;
        private String pair;
        private int pairId;
        private double ratio;
        private int tokenId;
        private String tokenImage;
        private String tokenName;
        private int transactionStatus;

        public double getIncrease() {
            return increase;
        }

        public void setIncrease(double increase) {
            this.increase = increase;
        }

        public String getPair() {
            return pair;
        }

        public void setPair(String pair) {
            this.pair = pair;
        }

        public int getPairId() {
            return pairId;
        }

        public void setPairId(int pairId) {
            this.pairId = pairId;
        }

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

        public int getTransactionStatus() {
            return transactionStatus;
        }

        public void setTransactionStatus(int transactionStatus) {
            this.transactionStatus = transactionStatus;
        }
    }
}
