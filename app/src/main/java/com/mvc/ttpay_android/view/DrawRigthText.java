package com.mvc.ttpay_android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.blankj.utilcode.util.LogUtils;

public class DrawRigthText extends android.support.v7.widget.AppCompatTextView {
    private Drawable mRigthDraw;

    public DrawRigthText(Context context) {
        this(context,null);
    }

    public DrawRigthText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawRigthText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable[] compoundDrawables = getCompoundDrawables();
        LogUtils.e("DrawRigthText", "compoundDrawables[2]:" + compoundDrawables[2] +"----------------"+mRigthDraw);
        if(compoundDrawables[2] != null  && mRigthDraw!=null){
            compoundDrawables[2] = mRigthDraw;
        }
    }
    public void setRight(Drawable rightDraw){
        this.mRigthDraw = rightDraw;
        invalidate();
    }
}
