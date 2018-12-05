package com.mvc.cryptovault_android.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CurrencyBean implements Parcelable {


    /**
     * code : 200
     * data : [{"timestamp":0,"tokenCnName":"VRT","tokenEnName":"VRT","tokenId":1,"tokenImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg","tokenName":"VRT","tokenType":1},{"timestamp":0,"tokenCnName":"余额","tokenEnName":"Balance","tokenId":2,"tokenImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg","tokenName":"余额","tokenType":0},{"timestamp":0,"tokenCnName":"以太坊","tokenEnName":"ethernum","tokenId":3,"tokenImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg","tokenName":"ETH","tokenType":2},{"timestamp":0,"tokenCnName":"泰达币","tokenEnName":"USDT","tokenId":4,"tokenImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg","tokenName":"USDT","tokenType":2},{"timestamp":0,"tokenCnName":"JYWD","tokenEnName":"JYWD","tokenId":5,"tokenImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg","tokenName":"JYWD","tokenType":2}]
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
         * timestamp : 0
         * tokenCnName : VRT
         * tokenEnName : VRT
         * tokenId : 1
         * tokenImage : https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg
         * tokenName : VRT
         * tokenType : 1
         */

        private int timestamp;
        private String tokenCnName;
        private String tokenEnName;
        private int tokenId;
        private String tokenImage;
        private String tokenName;
        private int tokenType;

        protected DataBean(Parcel in) {
            timestamp = in.readInt();
            tokenCnName = in.readString();
            tokenEnName = in.readString();
            tokenId = in.readInt();
            tokenImage = in.readString();
            tokenName = in.readString();
            tokenType = in.readInt();
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

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
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

        public int getTokenType() {
            return tokenType;
        }

        public void setTokenType(int tokenType) {
            this.tokenType = tokenType;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(timestamp);
            dest.writeString(tokenCnName);
            dest.writeString(tokenEnName);
            dest.writeInt(tokenId);
            dest.writeString(tokenImage);
            dest.writeString(tokenName);
            dest.writeInt(tokenType);
        }
    }
}
