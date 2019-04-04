package com.mvc.cryptovault_android.view;

import android.content.Context;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.utils.TextUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.mvc.cryptovault_android.common.Constant.SP.RECORDING_TYPE;

public class PopMarkerView extends MarkerView {
    private TextView mTimeMarker;
    private TextView mValueMarker;
    private final String recordingType;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public PopMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        recordingType = SPUtils.getInstance().getString(RECORDING_TYPE);
        mTimeMarker = findViewById(R.id.marker_time);
        mValueMarker = findViewById(R.id.marker_value);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        mTimeMarker.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(Long.valueOf(new BigDecimal(e.getX()).toString()))));
        mValueMarker.setText(TextUtils.doubleToEight(e.getY()) + " " + recordingType);
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        MPPointF offset = getOffset();
        Chart chart = getChartView();
        float width = getWidth();
        float height = getHeight();
// posY \posX 指的是markerView左上角点在图表上面的位置
//处理Y方向
        if (posY <= height) {// 如果点y坐标小于markerView的高度，如果不处理会超出上边界，处理了之后这时候箭头是向上的，我们需要把图标下移一个箭头的大小
            offset.y = 0;
        } else {//否则属于正常情况，因为我们默认是箭头朝下，然后正常偏移就是，需要向上偏移markerView高度和arrow size，再加一个stroke的宽度，因为你需要看到对话框的上面的边框
            offset.y = -height - 3; // 40 arrow height   5 stroke width
        }
//处理X方向，分为3种情况，1、在图表左边 2、在图表中间 3、在图表右边
//
        if (posX > chart.getWidth() - width) {//如果超过右边界，则向左偏移markerView的宽度
            offset.x = -width;
        } else {//默认情况，不偏移（因为是点是在左上角）
            offset.x = 0;
            if (posX > width / 2) {//如果大于markerView的一半，说明箭头在中间，所以向右偏移一半宽度
                offset.x = -(width / 2);
            }
        }
        return offset;
    }

    @Override
    public void setChartView(Chart chart) {
        super.setChartView(chart);
    }
}
