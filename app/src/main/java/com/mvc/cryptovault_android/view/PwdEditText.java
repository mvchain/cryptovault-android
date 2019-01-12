package com.mvc.cryptovault_android.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.utils.TextUtils;
import com.mvc.cryptovault_android.utils.ViewDrawUtils;

public class PwdEditText extends EditText {
    private Context context;

    public PwdEditText(Context context) {
        this(context, null);
    }

    public PwdEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PwdEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //按下就切换
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Drawable drawable = getCompoundDrawables()[2];
            if (drawable == null) {
                return super.onTouchEvent(event);
            }
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int width = getMeasuredWidth();
            if (event.getX() > (width - intrinsicWidth - 30) && event.getX() < width) {
                if (getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {
                    setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ViewDrawUtils.setRigthDraw(ContextCompat.getDrawable(context, R.drawable.edit_show), this);
                } else {
                    setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ViewDrawUtils.setRigthDraw(ContextCompat.getDrawable(context, R.drawable.edit_hide), this);
                }
//                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

}
