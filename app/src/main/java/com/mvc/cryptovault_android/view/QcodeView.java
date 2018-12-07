package com.mvc.cryptovault_android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class QcodeView extends View {
    public QcodeView(Context context) {
        this(context,null);
    }

    public QcodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QcodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {

    }

    @Override
    protected void onDraw(Canvas canvas) {

    }
}
