package com.mvc.cryptovault_android.view;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

public class CenterButton extends AppCompatRadioButton {
    public CenterButton(Context context) {
        this(context,null);
    }

    public CenterButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CenterButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
}
