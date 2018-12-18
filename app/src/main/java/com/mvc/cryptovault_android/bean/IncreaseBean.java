package com.mvc.cryptovault_android.bean;

public class IncreaseBean {
    private int currencyId;
    private boolean isAdd;
    private String resId;
    private String title;
    private String zhContent;
    private String enContent;
    private boolean isVisi;

    public String getEnContent() {
        return enContent;
    }

    public void setEnContent(String enContent) {
        this.enContent = enContent;
    }

    public boolean isVisi() {
        return isVisi;
    }

    public void setVisi(boolean visi) {
        isVisi = visi;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZhContent() {
        return zhContent;
    }

    public void setZhContent(String zhContent) {
        this.zhContent = zhContent;
    }
}
