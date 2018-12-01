package com.mvc.cryptovault_android.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CurrencyBean implements Parcelable {

    /**
     * code : 200
     * data : [{"timestamp":1542358128483,"tokenCnName":"VRT货币","tokenEnName":"VRT","tokenId":1,"tokenImage":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&","tokenName":"VRT"},{"timestamp":1542358128484,"tokenCnName":"余额","tokenEnName":"BALANCE","tokenId":2,"tokenImage":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&","tokenName":"余额"},{"timestamp":1542358128485,"tokenCnName":"以太坊","tokenEnName":"ETH","tokenId":3,"tokenImage":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&","tokenName":"ETH"},{"timestamp":1542358128486,"tokenCnName":"USDT","tokenEnName":"USDT","tokenId":4,"tokenImage":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&","tokenName":"USDT"},{"timestamp":0,"tokenCnName":"小牛","tokenEnName":"MVC0.8119309989","tokenId":9,"tokenImage":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&","tokenName":"MVC0.8119309989"},{"timestamp":0,"tokenCnName":"小牛","tokenEnName":"MVC0.9659708910","tokenId":10,"tokenImage":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&","tokenName":"MVC0.5131705911"},{"timestamp":0,"tokenCnName":"小牛","tokenEnName":"MVC0.0355440807","tokenId":11,"tokenImage":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&","tokenName":"MVC0.0355440807"},{"timestamp":0,"tokenCnName":"小牛","tokenEnName":"MVC0.7867652623","tokenId":12,"tokenImage":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&","tokenName":"MVC0.7867652623"},{"timestamp":0,"tokenCnName":"小牛","tokenEnName":"MVC0.8994572428","tokenId":13,"tokenImage":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&","tokenName":"MVC0.8994572428"},{"timestamp":0,"tokenCnName":"ETH2","tokenEnName":"ETH2","tokenId":14,"tokenImage":"string","tokenName":"ETH333"},{"timestamp":0,"tokenCnName":"中文","tokenEnName":"aa","tokenId":15,"tokenImage":"aaaaa","tokenName":"bb"},{"timestamp":0,"tokenCnName":"啊啊啊","tokenEnName":"aaa","tokenId":16,"tokenImage":"http://ico-list.oss-cn-hangzhou.aliyuncs.com/cryptovalut/201811271429244Ryr8arKyD.jpg","tokenName":"aaa"},{"timestamp":1542358128486,"tokenCnName":"JYWD","tokenEnName":"JYWD","tokenId":17,"tokenImage":"hangzhou.aliyuncs.com/cryptovalut/201811271429244Ryr8arKyD.jpg","tokenName":"JYWD"}]
     * message :
     */

    private int code;
    private String message;
    private List<DataBean> data;

    protected CurrencyBean(Parcel in) {
        code = in.readInt();
        message = in.readString();
        data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<CurrencyBean> CREATOR = new Creator<CurrencyBean>() {
        @Override
        public CurrencyBean createFromParcel(Parcel in) {
            return new CurrencyBean(in);
        }

        @Override
        public CurrencyBean[] newArray(int size) {
            return new CurrencyBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
        dest.writeTypedList(data);
    }

    public static class DataBean implements Parcelable {
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

        protected DataBean(Parcel in) {
            timestamp = in.readLong();
            tokenCnName = in.readString();
            tokenEnName = in.readString();
            tokenId = in.readInt();
            tokenImage = in.readString();
            tokenName = in.readString();
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(timestamp);
            dest.writeString(tokenCnName);
            dest.writeString(tokenEnName);
            dest.writeInt(tokenId);
            dest.writeString(tokenImage);
            dest.writeString(tokenName);
        }
    }
}
