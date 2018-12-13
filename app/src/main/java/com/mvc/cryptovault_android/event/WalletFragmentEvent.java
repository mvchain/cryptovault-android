package com.mvc.cryptovault_android.event;

public class WalletFragmentEvent {
    private int position;

    public WalletFragmentEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
