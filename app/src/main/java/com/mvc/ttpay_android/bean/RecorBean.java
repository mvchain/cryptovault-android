package com.mvc.ttpay_android.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class RecorBean {

    /**
     * code : 200
     * data : [{"headImage":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg","id":1,"limitValue":1000,"nickname":"暗黑佟大为","price":1,"total":1000}]
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

    public static class DataBean implements Serializable {
        /**
         * headImage : https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538598390,4205429837&fm=27&gp=0.jpg
         * id : 1
         * limitValue : 1000
         * nickname : 暗黑佟大为
         * price : 1
         * total : 1000
         * transactionType
         */

        private String headImage;
        private int id;
        private double limitValue;
        private String nickname;
        private BigDecimal price;
        private double total;
        private int transactionType;


        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public int getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(int transactionType) {
            this.transactionType = transactionType;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getLimitValue() {
            return limitValue;
        }

        public void setLimitValue(double limitValue) {
            this.limitValue = limitValue;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }


        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }
    }
}
