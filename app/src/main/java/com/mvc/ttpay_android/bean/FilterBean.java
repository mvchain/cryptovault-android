package com.mvc.ttpay_android.bean;

public class FilterBean {
    private boolean isCheck;
    private String title;
    private int pariId;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPariId() {
        return pariId;
    }

    public void setPariId(int pariId) {
        this.pariId = pariId;
    }
}
