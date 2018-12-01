package com.mvc.cryptovault_android.bean;

import java.util.List;

public class CurrencyBean {

    /**
     * code : 200
     * data : [{"timestamp":1542358128483,"tokenCnName":"VRT货币","tokenEnName":"VRT","tokenId":1,"tokenImage":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&","tokenName":"VRT"},{"timestamp":1542358128484,"tokenCnName":"余额","tokenEnName":"BALANCE","tokenId":2,"tokenImage":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&","tokenName":"余额"},{"timestamp":1542358128485,"tokenCnName":"以太坊","tokenEnName":"ETH","tokenId":3,"tokenImage":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&","tokenName":"ETH"},{"timestamp":1542358128486,"tokenCnName":"USDT","tokenEnName":"USDT","tokenId":4,"tokenImage":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&","tokenName":"USDT"},{"timestamp":0,"tokenCnName":"小牛","tokenEnName":"MVC0.8119309989","tokenId":9,"tokenImage":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&","tokenName":"MVC0.8119309989"},{"timestamp":0,"tokenCnName":"小牛","tokenEnName":"MVC0.9659708910","tokenId":10,"tokenImage":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&","tokenName":"MVC0.5131705911"},{"timestamp":0,"tokenCnName":"小牛","tokenEnName":"MVC0.0355440807","tokenId":11,"tokenImage":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&","tokenName":"MVC0.0355440807"},{"timestamp":0,"tokenCnName":"小牛","tokenEnName":"MVC0.7867652623","tokenId":12,"tokenImage":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&","tokenName":"MVC0.7867652623"},{"timestamp":0,"tokenCnName":"小牛","tokenEnName":"MVC0.8994572428","tokenId":13,"tokenImage":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&","tokenName":"MVC0.8994572428"},{"timestamp":0,"tokenCnName":"ETH2","tokenEnName":"ETH2","tokenId":14,"tokenImage":"string","tokenName":"ETH333"},{"timestamp":0,"tokenCnName":"中文","tokenEnName":"aa","tokenId":15,"tokenImage":"aaaaa","tokenName":"bb"},{"timestamp":0,"tokenCnName":"啊啊啊","tokenEnName":"aaa","tokenId":16,"tokenImage":"http://ico-list.oss-cn-hangzhou.aliyuncs.com/cryptovalut/201811271429244Ryr8arKyD.jpg","tokenName":"aaa"},{"timestamp":1542358128486,"tokenCnName":"JYWD","tokenEnName":"JYWD","tokenId":17,"tokenImage":"hangzhou.aliyuncs.com/cryptovalut/201811271429244Ryr8arKyD.jpg","tokenName":"JYWD"}]
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
         * timestamp : 1542358128483
         * tokenCnName : VRT货币
         * tokenEnName : VRT
         * tokenId : 1
         * tokenImage : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&
         * tokenName : VRT
         */

        private long timestamp;
        private String tokenCnName;
        private String tokenEnName;
        private int tokenId;
        private String tokenImage;
        private String tokenName;

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getTokenCnName() {
            return tokenCnName;
        }

        public void setTokenCnName(String tokenCnName) {
            this.tokenCnName = tokenCnName;
        }

        public String getTokenEnName() {
            return tokenEnName;
        }

        public void setTokenEnName(String tokenEnName) {
            this.tokenEnName = tokenEnName;
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
    }
}
