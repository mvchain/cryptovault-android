package com.mvc.cryptovault_android.bean;

import java.util.List;

public class KLineBean {

    /**
     * code : 200
     * message :
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
        private List<Long> timeX;
        private List<Float> valueY;

        public List<Long> getTimeX() {
            return timeX;
        }

        public void setTimeX(List<Long> timeX) {
            this.timeX = timeX;
        }

        public List<Float> getValueY() {
            return valueY;
        }

        public void setValueY(List<Float> valueY) {
            this.valueY = valueY;
        }
    }
}
