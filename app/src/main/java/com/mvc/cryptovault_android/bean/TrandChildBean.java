package com.mvc.cryptovault_android.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class TrandChildBean implements Parcelable{

    /**
     * code : 200
     * data : [{"increase":650,"pair":"VRT/USDT","pairId":1,"ratio":6.5,"tokenId":4,"tokenImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg","tokenName":"USDT","transactionStatus":0},{"increase":100,"pair":"VRT/JYWD","pairId":2,"ratio":1,"tokenId":5,"tokenImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg","tokenName":"JYWD","transactionStatus":1}]
     * message :
     */

    private int code;
    private String message;
    private ArrayList<DataBean> data;

    protected TrandChildBean(Parcel in) {
        code = in.readInt();
        message = in.readString();
        data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<TrandChildBean> CREATOR = new Creator<TrandChildBean>() {
        @Override
        public TrandChildBean createFromParcel(Parcel in) {
            return new TrandChildBean(in);
        }

        @Override
        public TrandChildBean[] newArray(int size) {
            return new TrandChildBean[size];
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

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
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

        protected DataBean(Parcel in) {
            increase = in.readDouble();
            pair = in.readString();
            pairId = in.readInt();
            ratio = in.readDouble();
            tokenId = in.readInt();
            tokenImage = in.readString();
            tokenName = in.readString();
            transactionStatus = in.readInt();
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(increase);
            dest.writeString(pair);
            dest.writeInt(pairId);
            dest.writeDouble(ratio);
            dest.writeInt(tokenId);
            dest.writeString(tokenImage);
            dest.writeString(tokenName);
            dest.writeInt(transactionStatus);
        }
    }
}
