package com.mvc.ttpay_android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import com.mvc.ttpay_android.R;

public class CenterButton extends AppCompatRadioButton {

    private boolean isCenterGravity = false;

    public CenterButton(Context context) {
        this(context, null);
    }

    public CenterButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CenterButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGravity(context, attrs);
    }

    private void initGravity(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CenterButton);
        //只有一个元素  所以直接下标为0
        isCenterGravity = typedArray.getBoolean(0, false);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //不注释的话会多绘制一次  需要屏蔽父类的绘制方法
//        super.onDraw(canvas);
        //对应 左/上/右/下  0/1/2/3
        Drawable[] compoundDrawables = getCompoundDrawables();
        Drawable startDrawble = compoundDrawables[0];
        if (startDrawble != null && isCenterGravity) {
            Bitmap bitmap = drawableToBitmap(startDrawble);
            canvas.drawBitmap(bitmap, ((getWidth() / 2) - (bitmap.getWidth() / 2)), ((getHeight() / 2) - (bitmap.getHeight() / 2)), new Paint());
        } else {
            super.onDraw(canvas);
        }
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();// 取drawable的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }
}
