package com.mvc.ttpay_android.utils;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

public class ViewDrawUtils {

    public static void setLeftDraw(Drawable draw, TextView view) {
        draw.setBounds(0, 0, draw.getMinimumWidth(), draw.getMinimumHeight());
        setBoundDraw(draw, null, null, null, view);
    }

    public static void setTopDraw(Drawable draw, TextView view) {
        draw.setBounds(0, 0, draw.getMinimumWidth(), draw.getMinimumHeight());
        setBoundDraw(null, draw, null, null, view);
    }

    public static void setRigthDraw(Drawable draw, TextView view) {
        draw.setBounds(0, 0, draw.getMinimumWidth(), draw.getMinimumHeight());
        setBoundDraw(null, null, draw, null, view);
    }

    public static void setBottomDraw(Drawable draw, TextView view) {
        draw.setBounds(0, 0, draw.getMinimumWidth(), draw.getMinimumHeight());
        setBoundDraw(null, null, null, draw, view);
    }

    public static void clearDraw(TextView view) {
        setBoundDraw(null, null, null, null, view);
    }

    private static void setBoundDraw(Drawable leftDraw, Drawable topDraw, Drawable rightDraw, Drawable bottomDraw, TextView view) {
        view.setCompoundDrawables(leftDraw, topDraw, rightDraw, bottomDraw);
    }
}
