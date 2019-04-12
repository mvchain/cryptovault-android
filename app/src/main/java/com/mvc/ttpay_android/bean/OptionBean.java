package com.mvc.ttpay_android.bean;

import java.util.List;

public class OptionBean {

    /**
     * code : 200
     * data : [{"baseTokenName":"USDT","id":9,"name":"cctv","partake":2,"times":11,"tokenName":"VRT","value":0},{"baseTokenName":"USDT","id":8,"name":"cctv","partake":5,"times":11,"tokenName":"VRT","value":0},{"baseTokenName":"USDT","id":7,"name":"cctv","partake":5,"times":11,"tokenName":"VRT","value":0}]
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
         * baseTokenName : USDT
         * id : 9
         * name : cctv
         * partake : 2
         * times : 11
         * tokenName : VRT
         * value : 0
         */

        private String baseTokenName;
        private int id;
        private String name;
        private double partake;
        private int times;
        private String tokenName;
        private double value;

        public String getBaseTokenName() {
            return baseTokenName;
        }

        public void setBaseTokenName(String baseTokenName) {
            this.baseTokenName = baseTokenName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPartake() {
            return partake;
        }

        public void setPartake(double partake) {
            this.partake = partake;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
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
