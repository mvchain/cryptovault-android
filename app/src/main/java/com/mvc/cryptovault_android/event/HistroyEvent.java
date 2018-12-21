package com.mvc.cryptovault_android.event;

public class HistroyEvent {
    private String price;

    public HistroyEvent(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
