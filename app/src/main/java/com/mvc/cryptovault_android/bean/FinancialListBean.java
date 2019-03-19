package com.mvc.cryptovault_android.bean;

import java.util.List;

public class FinancialListBean {

    /**
     * code : 0
     * data : [{"baseTokenName":"string","id":0,"incomeMax":0,"incomeMin":0,"minValue":0,"name":"string","stopAt":0,"times":0}]
     * message : string
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
         * baseTokenName : string
         * id : 0
         * incomeMax : 0
         * incomeMin : 0
         * minValue : 0
         * name : string
         * stopAt : 0
         * times : 0
         */

        private String baseTokenName;
        private int id;
        private double incomeMax;
        private double incomeMin;
        private double minValue;
        private String name;
        private long stopAt;
        private long times;

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

        public double getIncomeMax() {
            return incomeMax;
        }

        public void setIncomeMax(double incomeMax) {
            this.incomeMax = incomeMax;
        }

        public double getIncomeMin() {
            return incomeMin;
        }

        public void setIncomeMin(double incomeMin) {
            this.incomeMin = incomeMin;
        }

        public double getMinValue() {
            return minValue;
        }

        public void setMinValue(double minValue) {
            this.minValue = minValue;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getStopAt() {
            return stopAt;
        }

        public void setStopAt(long stopAt) {
            this.stopAt = stopAt;
        }

        public long getTimes() {
            return times;
        }

        public void setTimes(long times) {
            this.times = times;
        }
    }
}
