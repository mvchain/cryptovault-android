package com.mvc.cryptovault_android.bean;

public class UpdateBean {

    /**
     * code : 0
     * data : true
     * message : string
     */

    private int code;
    private boolean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
