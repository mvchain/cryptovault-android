package com.mvc.ttpay_android.event;

public class TrandOrderEvent {
    private boolean isRefresh;
    private String pariId;
    private String transactionType;

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getPariId() {
        return pariId;
    }

    public void setPariId(String pariId) {
        this.pariId = pariId;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }
}
