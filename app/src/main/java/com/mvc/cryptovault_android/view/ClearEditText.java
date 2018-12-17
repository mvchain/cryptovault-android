package com.mvc.cryptovault_android.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

public class ClearEditText extends EditText {

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //按下就清除
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Drawable drawable = getCompoundDrawables()[2];
            if (drawable == null) {
                return super.onTouchEvent(event);
            }
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int width = getMeasuredWidth();
            if(event.getX()>(width-intrinsicWidth-30) && event.getX()<width){
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

}
